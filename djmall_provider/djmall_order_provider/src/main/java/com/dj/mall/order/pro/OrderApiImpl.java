/*
 * 作者：CF
 * 日期：2020-07-29 18:06
 * 项目：djmall
 * 模块：djmall_order_api
 * 类名：OrderApi
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.order.pro;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.api.cart.ShoppingCartApi;
import com.dj.mall.auth.api.user.UserApi;
import com.dj.mall.auth.dto.address.UserAddressDTO;
import com.dj.mall.auth.dto.cart.ShoppingCartDTO;
import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.auth.dto.user.UserRoleDTO;
import com.dj.mall.cmpt.RedisApi;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.contant.DictConstant;
import com.dj.mall.model.contant.OrderConstant;
import com.dj.mall.model.contant.RedisConstant;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.VerifyCodeUtil;
import com.dj.mall.order.api.OrderApi;
import com.dj.mall.order.bo.OrderBO;
import com.dj.mall.order.bo.OrderInfoBO;
import com.dj.mall.order.dto.OrderDTO;
import com.dj.mall.order.dto.OrderDetailDTO;
import com.dj.mall.order.dto.OrderInfoDTO;
import com.dj.mall.order.entity.OrderDetailEntity;
import com.dj.mall.order.entity.OrderEntity;
import com.dj.mall.order.entity.OrderInfoEntity;
import com.dj.mall.order.mapper.OrderMapper;
import com.dj.mall.order.service.OrderDetailService;
import com.dj.mall.order.service.OrderInfoService;
import com.dj.mall.product.api.sku.SkuApi;
import com.dj.mall.product.api.spu.ProductApi;
import com.dj.mall.product.dto.sku.SkuDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.zookeeper.Login;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chengf
 * @date 2020/7/29 18:25
 * 主订单
 */
@Service
@Transactional(rollbackFor = Exception.class) //事务回滚
public class OrderApiImpl extends ServiceImpl<OrderMapper, OrderEntity> implements OrderApi {

    /**
     * 子订单
     */
    @Autowired
    private OrderInfoService orderInfoService;
    /**
     * 明细订单
     */
    @Autowired
    private OrderDetailService orderDetailService;
    /**
     * 用户
     */
    @Reference
    private UserApi userApi;
    /**
     * 购物车
     */
    @Reference
    private ShoppingCartApi shoppingCartApi;
    /**
     * 商品
     */
    @Reference
    private ProductApi productApi;
    /**
     * skuApi
     */
    @Reference
    private SkuApi skuApi;
    /**
     * redisApi
     */
    @Reference
    private RedisApi redisApi;
    /**
     * rabbitMQ
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 生成订单号 规则：DJ+当前时间+随机三位数字
     * @return 订单号
     */
    private String generateOrderNo() {
        String dateStr = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
        return OrderConstant.ORDER_NO_PREFIX + dateStr + VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_ONLY, 3, null);
    }

    /**
     * 新增订单
     * @param orderDTO orderDTO
     * @param TOKEN 令牌密钥 用户唯一标识
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public void addOrder(OrderDTO orderDTO, String TOKEN) throws Exception, BusinessException {
        //根据购物车中的skuId获取对应sku商品集合
        List<SkuDTO> skuList = skuApi.findSkuBySkuIds(orderDTO.getSkuIdList());
        for (int i = 0; i < skuList.size(); i++) {
            //商品sku AND 购买数量
            SkuDTO sku = skuList.get(i);
            Integer buyCount = orderDTO.getQuantityList().get(i);
            //如果商品库存小于购买数量 进行相关提示
            if (sku.getSkuCount() < buyCount) {
                throw new BusinessException("您购买的商品"+sku.getProductName()+"-"+sku.getSkuAttrValueNames()+"库存不足，请重新选购");
            }
            //库存 = 原库存 - 购买数量
            sku.setSkuCount(sku.getSkuCount() - buyCount);
            //金额计算需要：购买数量
            sku.setBuyCount(buyCount);
        }
        //根据skuId批量修改sku库存
        skuApi.updateSkuCountBatchByIds(skuList);
        //根据收货地址id获取收货地址数据
        UserAddressDTO userAddress = userApi.findAddressById(orderDTO.getAddressId());
        //主订单
        OrderEntity order = new OrderEntity();
        //子订单
        List<OrderInfoEntity> orderInfoList = new ArrayList<>();
        //明细订单
        List<OrderDetailEntity> orderDetailList = new ArrayList<>();
        //主订单赋值
        order.setOrderNo(generateOrderNo());
        order.setBuyerId(userAddress.getUserId());
        order.setPayType(orderDTO.getPayType());
        order.setReceiverProvince(userAddress.getProvinceShow());
        order.setReceiverCity(userAddress.getCityShow());
        order.setReceiverCounty(userAddress.getCountyShow());
        order.setReceiverName(userAddress.getReceiverName());
        order.setReceiverPhone(userAddress.getPhone());
        order.setReceiverDetail(userAddress.getDetailedAddress());
        order.setOrderStatus(DictConstant.PENDING_PAYMENT);
        order.setCreateTime(LocalDateTime.now());

        //通过商户进行分组
        Map<Integer, List<SkuDTO>> skuBusinessMap = skuList.stream().collect(Collectors.groupingBy(SkuDTO::getBusinessId));

        //主订单 总金额 总支付金额 总运费 总购买数
        BigDecimal totalMoney = BigDecimal.ZERO;
        BigDecimal totalPayMoney = BigDecimal.ZERO;
        BigDecimal totalFreight = BigDecimal.ZERO;
        Integer totalBuyCount = OrderConstant.ZERO;

        for (Map.Entry<Integer, List<SkuDTO>> skuEntry : skuBusinessMap.entrySet()) {
            OrderInfoEntity orderInfoEntity = DozerUtil.map(order, OrderInfoEntity.class);
            //子订单 商家 主订单号 订单号
            orderInfoEntity.setBusinessId(skuEntry.getKey()).setParentOrderNo(order.getOrderNo()).setOrderNo(generateOrderNo());

            //子订单 总金额 总支付金额 总运费 总购买数
            BigDecimal childTotalMoney = BigDecimal.ZERO;
            BigDecimal childTotalPayMoney = BigDecimal.ZERO;
            BigDecimal childTotalFreight = BigDecimal.ZERO;
            Integer childTotalBuyCount = OrderConstant.ZERO;

            for (int i = 0; i < skuEntry.getValue().size(); i++) {
                SkuDTO sku = skuEntry.getValue().get(i);
                Integer buyCount = sku.getBuyCount();
                //总金额 = 总金额 + (价格 x 购买数) + 运费
                childTotalMoney = childTotalMoney.add(sku.getSkuPrice().multiply(BigDecimal.valueOf(buyCount)).add(sku.getFreight()));
                //支付总金额 = 总金额 + (价格 x 购买数 x 折扣) + 运费
                childTotalPayMoney = childTotalPayMoney.add(sku.getSkuPrice().multiply(BigDecimal.valueOf(buyCount)).multiply(BigDecimal.valueOf(sku.getSkuRate() * 0.01)).add(sku.getFreight()));
                //总运费 = 总运费 + 运费
                childTotalFreight = childTotalFreight.add(sku.getFreight());
                //总购买数 = 总购买数 + 购买数
                childTotalBuyCount += buyCount;
                //子订单 总金额 总支付金额 总运费 总购买数
                orderInfoEntity.setProductId(sku.getProductId()).setTotalMoney(childTotalMoney).setTotalPayMoney(childTotalPayMoney).setTotalFreight(childTotalFreight).setTotalBuyCount(childTotalBuyCount);

                //明细订单 赋值
                OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
                orderDetailEntity.setChildOrderNo(orderInfoEntity.getOrderNo());
                orderDetailEntity.setParentOrderNo(order.getOrderNo());
                orderDetailEntity.setBuyerId(userAddress.getUserId());
                orderDetailEntity.setBusinessId(sku.getBusinessId());
                orderDetailEntity.setProductId(sku.getProductId());
                orderDetailEntity.setSkuId(sku.getSkuId());
                orderDetailEntity.setSkuInfo(sku.getSkuAttrValueNames());
                orderDetailEntity.setSkuPrice(sku.getSkuPrice());
                orderDetailEntity.setSkuRate(sku.getSkuRate());
                orderDetailEntity.setBuyCount(buyCount);
                orderDetailEntity.setPayMoney(sku.getSkuPrice().multiply(BigDecimal.valueOf(buyCount)).multiply(BigDecimal.valueOf(sku.getSkuRate() * 0.01)).add(sku.getFreight()));
                orderDetailEntity.setSkuFreight(sku.getFreight());
                orderDetailEntity.setCreateTime(LocalDateTime.now());
                orderDetailEntity.setProductName(sku.getProductName());
                orderDetailEntity.setIsComment(OrderConstant.NOT_COMMENT);
                orderDetailList.add(orderDetailEntity);
            }
            //主订单 总金额 总支付金额 总运费 总购买数
            totalMoney = totalMoney.add(childTotalMoney);
            totalPayMoney = totalPayMoney.add(childTotalPayMoney);
            totalFreight = totalFreight.add(childTotalFreight);
            totalBuyCount += childTotalBuyCount;
            orderInfoList.add(orderInfoEntity);
        }
        //主订单赋值
        order.setTotalMoney(totalMoney);
        order.setTotalPayMoney(totalPayMoney);
        order.setTotalFreight(totalFreight);
        order.setTotalBuyCount(totalBuyCount);
        //新增 主订单 子订单 订单明细
        this.save(order);
        orderInfoService.saveBatch(orderInfoList);
        orderDetailService.saveBatch(orderDetailList);

        // 下单后30分钟未支付直接取消订单
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDelay(30 * 60 * 1000);
        Message message = new Message(order.getOrderNo().getBytes(), messageProperties);
        rabbitTemplate.convertAndSend("order-dlx-ex", "orderDlxQueue", message);

        //清除购物车
        List<Integer> cartIdList = orderDTO.getCartIdList();
        if (null != cartIdList && cartIdList.size() > OrderConstant.ZERO) {
            Integer[] ids = new Integer[cartIdList.size()];
            shoppingCartApi.deleteCartById(cartIdList.toArray(ids));
        }
    }

    /**
     * 查找主订单的全部数据
     * @param orderDTO orderDTO
     * @param TOKEN 令牌密钥 用户唯一标识
     * @return  List<OrderDTO>
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public PageResult findOrderAll(OrderDTO orderDTO, String TOKEN) throws Exception, BusinessException {
        Page<OrderEntity> page = new Page<>(orderDTO.getPageNo(), orderDTO.getPageSize());
        UserDTO user = redisApi.get(RedisConstant.USER_TOKEN + TOKEN);
        IPage<OrderBO> iPage = baseMapper.findOrderAll(page, user.getUserId());
        return new PageResult().toBuilder().pages(iPage.getPages()).list(DozerUtil.mapList(iPage.getRecords(), OrderDTO.class)).build();
    }

    /**
     *根据用户id和订单状态查找子订单数据
     * @param pageNo 起始页
     * @param orderStatus 订单状态
     * @param TOKEN 令牌密钥 用户唯一标识
     * @return ResultModel
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public PageResult getOrderInfo(Integer pageNo, String orderStatus, String TOKEN) throws Exception, BusinessException {
        Page<OrderEntity> page = new Page<>(pageNo, OrderConstant.PAGE_SIZE);
        UserDTO user = redisApi.get(RedisConstant.USER_TOKEN + TOKEN);
        IPage<OrderInfoBO> iPage = baseMapper.getOrderInfoByUserIdAndOrderStatus(page, orderStatus, user.getUserId());
        return new PageResult().toBuilder().pages(iPage.getPages()).list(DozerUtil.mapList(iPage.getRecords(), OrderInfoDTO.class)).build();
    }

    /**
     * 订单详情展示
     * @param orderNo 订单号
     * @return OrderDTO
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public OrderDTO findOrderByOrderNo(String orderNo) throws Exception, BusinessException {
        return DozerUtil.map(baseMapper.findOrderByOrderNo(orderNo), OrderDTO.class);
    }

    /**
     * 子订单详情展示
     * @param orderNo 订单号
     * @return OrderDTO
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public OrderInfoDTO findOrderInfoByOrderNo(String orderNo) throws Exception, BusinessException {
        return DozerUtil.map(baseMapper.findOrderInfoByOrderNo(orderNo), OrderInfoDTO.class);
    }

    /**
     * 修改订单状态 取消订单
     * @param orderNo 订单号
     * @param orderStatus 订单状态
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public void updateStatus(String orderNo, String orderStatus) throws Exception, BusinessException {
        //修改子订单状态
        UpdateWrapper<OrderInfoEntity> orderInfoUpdateWrapper = new UpdateWrapper<>();
        if (DictConstant.CANCELLED.equals(orderStatus)) {
            //修改主订单状态
            UpdateWrapper<OrderEntity> updateWrapper = new UpdateWrapper<OrderEntity>().eq("order_no", orderNo);
            updateWrapper.set("order_status", orderStatus);
            update(updateWrapper);
            //取消订单修改库存
            if (DictConstant.CANCELLED.equals(orderStatus)) {
                baseMapper.updateCount(orderNo);
            }
            orderInfoUpdateWrapper.eq("parent_order_no", orderNo);
        } else {
            orderInfoUpdateWrapper.eq("order_no", orderNo);
        }
        orderInfoUpdateWrapper.set("order_status", orderStatus);
        orderInfoService.update(orderInfoUpdateWrapper);
    }

    /**
     * 提醒发货
     * @param orderNo 订单号
     * @param remindStatus 提醒状态 1 以提醒
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public void updateRemind(String orderNo, Integer remindStatus) throws Exception, BusinessException {
        UpdateWrapper<OrderInfoEntity> updateWrapper = new UpdateWrapper<OrderInfoEntity>().eq("order_no", orderNo);
        updateWrapper.set("remind_status", remindStatus);
        if (OrderConstant.NOT_REMIND_STATUS.equals(remindStatus)) {
            updateWrapper.set("remind_time", LocalDateTime.now().plusHours(OrderConstant.REMIND_TIME));
        }
        orderInfoService.update(updateWrapper);
    }

    /**
     * 再次购买
     * @param orderNo 订单号
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public void addCart(String orderNo) throws Exception, BusinessException {
        //根基子订单号得到明细数据
        List<OrderDetailEntity> orderDetailEntityList = orderDetailService.list(new QueryWrapper<OrderDetailEntity>().eq("child_order_no", orderNo));
        List<ShoppingCartDTO> cartList = new ArrayList<>();
        orderDetailEntityList.forEach(detail -> {
            ShoppingCartDTO cart = new ShoppingCartDTO().toBuilder()
                    .userId(detail.getBuyerId()).productId(detail.getProductId())
                    .skuId(detail.getSkuId()).quantity(detail.getBuyCount()).checked(OrderConstant.ZERO).build();
            cartList.add(cart);
        });
        //批量新增购物车
        userApi.saveCartBatch(cartList);
    }

    /**
     * admin订单展示
     * @param orderInfoDTO 子订单dto
     * @return PageResult
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public PageResult adminShow(OrderInfoDTO orderInfoDTO) throws Exception, BusinessException {
        Page<OrderInfoEntity> page = new Page<>(orderInfoDTO.getPageNo(), orderInfoDTO.getPageSize());
        IPage<OrderInfoBO> iPage = baseMapper.adminShow(page, DozerUtil.map(orderInfoDTO, OrderInfoBO.class));
        return new PageResult().toBuilder().pages(iPage.getPages()).list(DozerUtil.mapList(iPage.getRecords(), OrderInfoDTO.class)).build();
    }

    /**
     * 订单评论
     * @param orderNo 子订单号
     * @return OrderDetailDTO集合数据
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public List<OrderDetailDTO> findOrderDetailByChildOrderNo(String orderNo) throws Exception, BusinessException {
        QueryWrapper<OrderDetailEntity> queryWrapper = new QueryWrapper<>();
        //根据子订单号 以及 是否评论：0未评论 查 商品id 商品名 商品属性 id
        queryWrapper.eq("child_order_no", orderNo);
        queryWrapper.eq("is_comment", OrderConstant.NOT_COMMENT);
        queryWrapper.select("product_id", "product_name", "sku_info", "id");
        return DozerUtil.mapList(orderDetailService.list(queryWrapper), OrderDetailDTO.class);
    }

    /**
     * 商户登录消息提醒
     * @return List<OrderDetailDTO>
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public List<OrderInfoDTO> showInform(Integer userId) throws Exception, BusinessException {
        List<OrderInfoEntity> orderInfoEntityList = orderInfoService.list(new QueryWrapper<OrderInfoEntity>()
                .and(i -> i.eq("remind_status", OrderConstant.NOT_REMIND_STATUS).eq("business_id", userId)));
        return DozerUtil.mapList(orderInfoEntityList, OrderInfoDTO.class);
    }

    /**
     * 导出订单
     * @param orderInfoDTO orderInfoDTO
     * @return  byte[]
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public byte[] exportOrder(OrderInfoDTO orderInfoDTO) throws Exception, BusinessException {
        Page<OrderInfoEntity> page = new Page<>(1, 100);
        //查询所有数据
        List<OrderInfoBO> orderList = baseMapper.adminShow(page, DozerUtil.map(orderInfoDTO, OrderInfoBO.class)).getRecords();
        //按照状态进行分组
        Map<String, List<OrderInfoBO>> collect = orderList.stream().collect(Collectors.groupingBy(OrderInfoBO::getOrderStatusShow));
        //新建工作簿
        Workbook workbook = new XSSFWorkbook();
        //将订单数据写入工作表
        for (Map.Entry<String, List<OrderInfoBO>> entry : collect.entrySet()) {
            createSheet(workbook, entry.getKey(), entry.getValue());
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        return bos.toByteArray();
    }

    /**
     * 修改订单评论状态
     * @param detailIdList 明细订单id集合
     * @param isComment 订单评论状态 1 已评论
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public void updateDetailComment(List<Integer> detailIdList, Integer isComment) throws Exception, BusinessException {
        UpdateWrapper<OrderDetailEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_comment", isComment);
        updateWrapper.in("id", detailIdList);
        orderDetailService.update(updateWrapper);
    }

    /**
     * 根据子订单号查该订单得评论是否全部完成
     * @param orderNo 子订单号
     * @return  Integer
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public Integer findAllIsCommentByChildOrderNo(String orderNo) throws Exception, BusinessException {
        return baseMapper.findAllIsCommentByChildOrderNo(orderNo);
    }

    /**
     * 创建sheet
     * @param workbook 工作簿
     * @param orderStatus 订单状态
     * @param orderList 子订单集合
     */
    private void createSheet(Workbook workbook, String orderStatus, List<OrderInfoBO> orderList) {
        //订单创建时间
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //创建页
        Sheet sheet = workbook.createSheet(orderStatus);
        sheet.setColumnWidth(0, 25*256);//给第1列设置10个字的宽度
        sheet.setColumnWidth(1, 50*256);//给第1列设置10个字的宽度
        //创建行
        Row title = sheet.createRow(0);
        title.createCell(0).setCellValue("订单号");
        title.createCell(1).setCellValue("商品名称");
        title.createCell(2).setCellValue("购买数量");
        //如果是待支付和已取消
        if ("待支付".equals(orderStatus) || "已取消".equals(orderStatus)) {
            sheet.setColumnWidth(3, 20*256);//给第1列设置10个字的宽度
            sheet.setColumnWidth(5, 20*256);//给第1列设置10个字的宽度
            sheet.setColumnWidth(6, 25*256);//给第1列设置10个字的宽度
            title.createCell(3).setCellValue("待付款金额（包含邮费）");
            title.createCell(4).setCellValue("下单人");
            title.createCell(5).setCellValue("下单人电话");
            title.createCell(6).setCellValue("下单时间");
            if ("已取消".equals(orderStatus)) {
                sheet.setColumnWidth(7, 25*256);//给第1列设置10个字的宽度
                title.createCell(7).setCellValue("取消时间");
            }
            // 构建表格数据
            for (int i = 0; i < orderList.size(); i++) {
                Row row = sheet.createRow(i + 1);
                OrderInfoBO orderInfoBO = orderList.get(i);
                row.createCell(0).setCellValue(orderInfoBO.getOrderNo());
                row.createCell(1).setCellValue(orderInfoBO.getProductNameShow());
                row.createCell(2).setCellValue(orderInfoBO.getTotalBuyCount());
                row.createCell(3).setCellValue(orderInfoBO.getTotalPayMoney().toString());
                row.createCell(4).setCellValue(orderInfoBO.getNickNameShow());
                row.createCell(5).setCellValue(orderInfoBO.getUserPhoneShow());
                row.createCell(6).setCellValue(dateTimeFormatter.format(orderInfoBO.getCreateTime()));
                if ("已取消".equals(orderStatus)) {
                    row.createCell(7).setCellValue(dateTimeFormatter.format(orderInfoBO.getUpdateTime()));
                }
            }
        } else {
            sheet.setColumnWidth(3, 20*256);//给第1列设置10个字的宽度
            sheet.setColumnWidth(5, 20*256);//给第1列设置10个字的宽度
            sheet.setColumnWidth(6, 45*256);//给第1列设置10个字的宽度
            sheet.setColumnWidth(8, 20*256);//给第1列设置10个字的宽度
            sheet.setColumnWidth(9, 25*256);//给第1列设置10个字的宽度
            sheet.setColumnWidth(10, 25*256);//给第1列设置10个字的宽度

            title.createCell(3).setCellValue("付款金额（包含邮费）");
            title.createCell(4).setCellValue("支付方式");
            title.createCell(5).setCellValue("邮费");
            title.createCell(6).setCellValue("收货人信息");
            title.createCell(7).setCellValue("下单人");
            title.createCell(8).setCellValue("下单人电话");
            title.createCell(9).setCellValue("下单时间");
            title.createCell(10).setCellValue("付款时间");
            if (!"已支付".equals(orderStatus)) {
                sheet.setColumnWidth(11, 25*256);//给第1列设置10个字的宽度
                title.createCell(11).setCellValue("收货时间");
            }
            // 构建表格数据
            for (int i = 0; i < orderList.size(); i++) {
                Row row = sheet.createRow(i + 1);
                OrderInfoBO orderInfoBO = orderList.get(i);
                row.createCell(0).setCellValue(orderInfoBO.getOrderNo());
                row.createCell(1).setCellValue(orderInfoBO.getProductNameShow());
                row.createCell(2).setCellValue(orderInfoBO.getTotalBuyCount());
                row.createCell(3).setCellValue(orderInfoBO.getTotalPayMoney().toString());
                row.createCell(4).setCellValue(orderInfoBO.getPayTypeShow());
                row.createCell(5).setCellValue(orderInfoBO.getTotalFreight().toString());
                row.createCell(6).setCellValue(orderInfoBO.getReceiverName()+"-"+orderInfoBO.getReceiverPhone()+"-"+orderInfoBO.getReceiverProvince()+orderInfoBO.getReceiverCity()+orderInfoBO.getReceiverCounty()+orderInfoBO.getReceiverDetail());
                row.createCell(7).setCellValue(orderInfoBO.getNickNameShow());
                row.createCell(8).setCellValue(orderInfoBO.getUserPhoneShow());
                row.createCell(9).setCellValue(dateTimeFormatter.format(orderInfoBO.getCreateTime()));
                //row.createCell(10).setCellValue(dateTimeFormatter.format(orderInfoBO.getPayTime()));
                if (!"已支付".equals(orderStatus)) {
                    row.createCell(11).setCellValue(dateTimeFormatter.format(orderInfoBO.getUpdateTime()));
                }
            }
        }
    }
}