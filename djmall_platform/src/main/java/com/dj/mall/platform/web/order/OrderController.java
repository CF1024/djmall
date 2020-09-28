/*
 * 作者：CF
 * 日期：2020-07-30 15:47
 * 项目：djmall
 * 模块：djmall_platform
 * 类名：OrderController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.platform.web.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.dj.mall.auth.api.cart.ShoppingCartApi;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.contant.DictConstant;
import com.dj.mall.model.contant.OrderConstant;
import com.dj.mall.model.util.AliPayUtils;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.order.api.OrderApi;
import com.dj.mall.order.dto.OrderDTO;
import com.dj.mall.order.dto.OrderDetailDTO;
import com.dj.mall.platform.vo.order.OrderDetailVoResp;
import com.dj.mall.platform.vo.order.OrderInfoVoResp;
import com.dj.mall.platform.vo.order.OrderVoReq;
import com.dj.mall.platform.vo.order.OrderVoResp;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chengf
 * @date 2020/7/30 15:48
 * 订单
 */
@RestController
@RequestMapping("/order/")
public class OrderController {
    /**
     * 主订单api
     */
    @Reference
    private OrderApi orderApi;
    /**
     * 购物车
     */
    @Reference
    private ShoppingCartApi shoppingCartApi;

    /**
     *
     * @param orderVoReq OrderVoReq
     * @param TOKEN 令牌密钥 用户唯一标识
     * @return ResultModel
     * @throws Exception 异常
     */
    @PostMapping("addOrder")
    public ResultModel<Object> addOrder(OrderVoReq orderVoReq, String TOKEN) throws Exception {
        String orderNo = orderApi.addOrder(DozerUtil.map(orderVoReq, OrderDTO.class), TOKEN);
        return new ResultModel<>().success(orderNo);
    }

    /**
     * 异步调用 修改状态 已支付
     * @param request HttpServletRequest
     * @return success
     * @throws Exception 异常
     */
    @RequestMapping("aliPayCallBack")
    public synchronized String aliPayCallBack(HttpServletRequest request) throws Exception {
        Map<String, String> map = AliPayUtils.aliPayCallBack(request);
        if(null == map || map.isEmpty()){
            return "error";
        }
        // 1:获取到你传入支付宝的订单号
        String orderNo = map.get("out_trade_no");
        String aliPayTradeNo = map.get("ali_trade_no");
        // 2:修改订单状态 为 已支付
        orderApi.updateStatus(orderNo, DictConstant.PAID, aliPayTradeNo);
        return "success";
    }
    /**
     * 查找主订单的全部数据
     * @param orderVoReq orderDTO
     * @param TOKEN 令牌密钥 用户唯一标识
     * @return  ResultModel
     * @throws Exception 异常
     */
    @PostMapping("showOrder")
    public ResultModel<Object> showOrder(OrderVoReq orderVoReq, @RequestHeader(OrderConstant.TOKEN) String TOKEN) throws Exception {
        PageResult pageResult = orderApi.findOrderAll(DozerUtil.map(orderVoReq, OrderDTO.class), TOKEN);
        return new ResultModel<>().success(pageResult.toBuilder().list(DozerUtil.mapList(pageResult.getList(), OrderVoResp.class)).build());
    }

    /**
     *待收货/已完成/已取消订单展示
     * @param pageNo 起始页
     * @param orderStatus 订单状态
     * @param TOKEN 令牌密钥 用户唯一标识
     * @return ResultModel
     * @throws Exception 异常
     */
    @PostMapping("getOrderInfo")
    public ResultModel<Object> getOrderInfo(Integer pageNo, String orderStatus, @RequestHeader(OrderConstant.TOKEN) String TOKEN) throws Exception {
        PageResult pageResult = orderApi.getOrderInfo(pageNo, orderStatus, TOKEN);
        return new ResultModel<>().success(pageResult.toBuilder().list(DozerUtil.mapList(pageResult.getList(), OrderInfoVoResp.class)).build());
    }

    /**
     * 修改订单状态
     * @param orderNo 订单号
     * @param orderStatus 订单状态
     * @return ResultModel
     * @throws Exception 异常
     */
    @PutMapping("updateStatus")
    public ResultModel<Object> updateStatus(String orderNo, String orderStatus) throws Exception {
        orderApi.updateStatus(orderNo, orderStatus, null);
        return new ResultModel<>().success(true);
    }

    /**
     * 提醒发货
     * @param orderNo 订单号
     * @param remindStatus 提醒状态 1
     * @return  ResultModel
     * @throws Exception 异常
     */
    @PutMapping("updateRemind")
    public ResultModel<Object> updateRemind(String orderNo, Integer remindStatus) throws Exception {
        orderApi.updateRemind(orderNo, remindStatus);
        return new ResultModel<>().success(true);
    }

    /**
     * 加入购物车 再次购买
     * @param orderNo 订单号
     * @return ResultModel
     * @throws Exception 异常
     */
    @PostMapping("buyAgain")
    public ResultModel<Object> buyAgain(String orderNo) throws Exception {
        orderApi.addCart(orderNo);
        return new ResultModel<>().success(true);
    }

    /**
     * 去支付
     * @param orderNo 订单号
     * @return ailPay
     * @throws Exception 异常
     */
    @RequestMapping("toPay")
    public String toPay(String orderNo) throws Exception {
        OrderDTO order = orderApi.findOrderByOrderNo(orderNo);
        List<OrderDetailDTO> orderDetailList = order.getDetailList();
        //商品名称
        StringBuilder productName = new StringBuilder();
        orderDetailList.forEach(orderDetail -> productName.append(orderDetail.getProductName()).append(","));
        return AliPayUtils.toAliPay(order.getOrderNo(), order.getTotalPayMoney().doubleValue(), productName.toString().substring(0, (productName.toString()).length() - 1));
    }

    /**
     * 立即购买取消订单删除购物车
     * @param orderVoReq
     * @return
     * @throws Exception
     */
    @PostMapping("cancelOrder")
    public ResultModel<Object> cancelOrder(OrderVoReq orderVoReq) throws Exception {
        List<Integer> cartIdList = orderVoReq.getCartIdList();
        if (null != cartIdList && cartIdList.size() > OrderConstant.ZERO) {
            Integer[] ids = new Integer[cartIdList.size()];
            shoppingCartApi.deleteCartById(cartIdList.toArray(ids));
        }
        return new ResultModel<>().success(true);
    }
}
