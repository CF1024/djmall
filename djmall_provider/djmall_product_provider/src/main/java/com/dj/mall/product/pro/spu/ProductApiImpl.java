/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_product_provider
 * 类名：ProductApiImpl
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.product.pro.spu;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.cmpt.RedisApi;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.contant.*;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.HttpClientUtil;
import com.dj.mall.model.util.POIUtil;
import com.dj.mall.model.util.QiniuUtils;
import com.dj.mall.order.api.OrderApi;
import com.dj.mall.product.api.spu.ProductApi;
import com.dj.mall.product.bo.reviews.ProductReviewsBO;
import com.dj.mall.product.bo.spu.ProductBO;
import com.dj.mall.product.config.ProductSolr;
import com.dj.mall.product.dto.reviews.ProductReviewsDTO;
import com.dj.mall.product.dto.sku.SkuDTO;
import com.dj.mall.product.dto.spu.ProductDTO;
import com.dj.mall.product.entity.like.ProductLikeEntity;
import com.dj.mall.product.entity.reviews.ProductReviewsEntity;
import com.dj.mall.product.entity.sku.SkuEntity;
import com.dj.mall.product.entity.spu.ProductEntity;
import com.dj.mall.product.mapper.spu.ProductMapper;
import com.dj.mall.product.service.like.ProductLikeService;
import com.dj.mall.product.service.reviews.ProductReviewsService;
import com.dj.mall.product.service.sku.SkuService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chengf
 * @date 2020/7/2 15:16
 * 商品ApiImpl
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductApiImpl extends ServiceImpl<ProductMapper, ProductEntity> implements ProductApi {
    /**
     * 不暴露服务 skuService
     */
    @Autowired
    private SkuService skuService;
    /**
     * SolrClient 搜索服务调用
     */
    @Autowired
    private SolrClient solrClient;
    /**
     * 商品点赞service
     */
    @Autowired
    private ProductLikeService productLikeService;
    /**
     * 商品评论service
     */
    @Autowired
    private ProductReviewsService productReviewsService;
    /**
     * orderApi
     */
    @Reference
    private OrderApi orderApi;


    /**
     * 商品展示
     * @param productDTO 商品dto
     * @return PageResult
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public PageResult findAll(ProductDTO productDTO) throws Exception, BusinessException {
        Page<ProductEntity> page = new Page<>(productDTO.getPageNo(), productDTO.getPageSize());
        IPage<ProductBO>  iPage = getBaseMapper().findAll(page, DozerUtil.map(productDTO, ProductBO.class));
        return new PageResult().toBuilder().pages(iPage.getPages()).list(DozerUtil.mapList(iPage.getRecords(), ProductDTO.class)).build();
    }

    /**
     * 去重
     * @param productDTO 商品Dto
     * @return Boolean
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public Boolean deDuplicate(ProductDTO productDTO) throws Exception, BusinessException {
        QueryWrapper<ProductEntity> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(productDTO.getProductId())) {
            queryWrapper.ne("id", productDTO.getProductId());
        }
        if (!StringUtils.isEmpty(productDTO.getProductName())) {
            queryWrapper.eq("product_name", productDTO.getProductName());
        }
        return this.getOne(queryWrapper) == null;
    }

    /**
     * 商品新增
     * @param productDTO 商品Dto
     * @param file byte流文件
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public void addProduct(ProductDTO productDTO, byte[] file) throws Exception, BusinessException {
        ProductEntity productEntity = DozerUtil.map(productDTO, ProductEntity.class);
        this.save(productEntity);
        List<SkuEntity> skuEntityList = DozerUtil.mapList(productDTO.getProductSkuList(), SkuEntity.class);
        skuEntityList.forEach(skuEntity -> {
            skuEntity.setProductId(productEntity.getId());
            skuEntity.setSkuStatus(DictConstant.PRODUCT_STATUS_UP);
            skuEntity.setIsDefault(DictConstant.NOT_DEFAULT);
        });
        //集合下标为0的sku数据的是否默认：设为默认
        skuEntityList.get(AuthConstant.ZERO).setIsDefault(DictConstant.HAVE_DEFAULT);
        skuService.saveBatch(skuEntityList);
        //七牛云上传
        InputStream inputStream = new ByteArrayInputStream(file);
        QiniuUtils.uploadByInputStream(inputStream, productEntity.getProductImg());
    }

    /**
     * 根据id修改商品和商品sku上下架状态
     * @param id 商品ID
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public void updateProductStatusById(Integer id) throws Exception, BusinessException {
        //根据id获取商品信息 和 商品sku的信息
        ProductEntity productEntity = this.getById(id);
        List<SkuEntity> skuEntityList = skuService.list(new QueryWrapper<SkuEntity>().eq("product_id", id));
        //商品修改 商品sku批量修改 上架 && 下架
        if (DictConstant.PRODUCT_STATUS_UP.equals(productEntity.getProductStatus())) {
            productEntity.setProductStatus(DictConstant.PRODUCT_STATUS_DOWN);
            skuEntityList.forEach(skuEntity -> skuEntity.setSkuStatus(DictConstant.PRODUCT_STATUS_DOWN));
            this.updateById(productEntity);
            skuService.updateBatchById(skuEntityList);
            return;
        }
        if (DictConstant.PRODUCT_STATUS_DOWN.equals(productEntity.getProductStatus())) {
            productEntity.setProductStatus(DictConstant.PRODUCT_STATUS_UP);
            skuEntityList.forEach(skuEntity -> skuEntity.setSkuStatus(DictConstant.PRODUCT_STATUS_UP));
            this.updateById(productEntity);
            skuService.updateBatchById(skuEntityList);
        }


    }

    /**
     * 根据id查商品信息
     * @param productId 商品ID
     * @return ProductDTO
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public ProductDTO findProductById(Integer productId) throws Exception, BusinessException {
        return DozerUtil.map(this.getById(productId), ProductDTO.class);
    }

    /**
     * 根据id修改商品
     * @param productDTO productDTO
     * @param file 图片文件流
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public void updateProductById(ProductDTO productDTO, byte[] file) throws Exception, BusinessException {
        this.updateById(DozerUtil.map(productDTO, ProductEntity.class));
        //七牛云删除之前的以及上传新的
        if (!StringUtils.isEmpty(file)) {
            QiniuUtils.delFile(productDTO.getRemoveImg());
            InputStream inputStream = new ByteArrayInputStream(file);
            QiniuUtils.uploadByInputStream(inputStream, productDTO.getProductImg());
        }
    }

    /**
     * 增量索引
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public void incrementalIndex() throws Exception, BusinessException {
        HttpClientUtil.sendHttpRequest("http://localhost:8085/solr/SolrCore/dataimport?command=delta-import", HttpClientUtil.HttpRequestMethod.GET, new HashMap<>());
    }

    /**
     * 重构索引
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public void refactoringTheIndex() throws Exception, BusinessException {
        HttpClientUtil.sendHttpRequest("http://localhost:8085/solr/SolrCore/dataimport?command=full-import", HttpClientUtil.HttpRequestMethod.GET, new HashMap<>());
    }

    /*==============================================商城==================================================*/

    /**
     * 商城展示
     * @param productDTO  productDTO
     * @return PageResult
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public PageResult findList(ProductDTO productDTO) throws Exception, BusinessException {
        ProductSolr productSolr = DozerUtil.map(productDTO, ProductSolr.class);
        SolrQuery query = new SolrQuery();
        //输入框查询 或 查询全部
        if (!StringUtils.isEmpty(productSolr.getProductKeywords())) {
            query.setQuery("productKeywords:"+productSolr.getProductKeywords());
        } else {
            query.setQuery("*:*");
        }

        //价格查询
        if (null != productSolr.getSkuPriceMin() && null == productSolr.getSkuPriceMax()) {
            query.addFilterQuery("skuPrice : ["+ productSolr.getSkuPriceMin() +" TO *]");
        }
        if (null == productSolr.getSkuPriceMin() && null != productSolr.getSkuPriceMax()) {
            query.addFilterQuery("skuPrice : [* TO "+ productSolr.getSkuPriceMax() +"]");
        }
        if (null != productSolr.getSkuPriceMin() && null != productSolr.getSkuPriceMax()) {
            query.addFilterQuery("skuPrice : ["+ productSolr.getSkuPriceMin() +" TO "+ productSolr.getSkuPriceMax() +"]");
        }

        //复选框查询
        List<String> productTypeList = productSolr.getProductTypeList();
        if (null != productTypeList && ProductConstant.ZERO < productTypeList.size()) {
            StringBuilder filter = new StringBuilder("productType:");
            for (String productType : productTypeList) {
                filter.append(productType).append(",");
            }
            query.addFilterQuery(filter.substring(0, filter.length() -1));
        }

        //排序条件
        List<SolrQuery.SortClause> solrList = new ArrayList<>();
        solrList.add(new SolrQuery.SortClause("id", SolrQuery.ORDER.desc));
        //根据多字段排序
        query.setSorts(solrList);

        //分页 起始页：setStart 每页条数：setRows
        query.setStart(productDTO.getPageSize() * (productDTO.getPageNo() - 1));
        query.setRows(productDTO.getPageSize());

        //高亮查询
        query.setHighlight(true); //开启高亮
        //高亮字段
        query.addHighlightField("productName");
        query.addHighlightField("productType");
        query.addHighlightField("productDescribe");
        query.addHighlightField("skuAttrValueNames");
        //高亮前后缀
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");

        QueryResponse queryResponse  = solrClient.query(query);

        //获得数据
        List<ProductSolr> productSolrList = queryResponse.getBeans(ProductSolr.class);
        List<Integer> productIds = new ArrayList<>();

        //获得高亮数据
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        productSolrList.forEach(product -> {
            productIds.add(Integer.valueOf(product.getProductId()));
            Map<String, List<String>> listMap = highlighting.get(product.getProductId());
            if (!StringUtils.isEmpty(listMap)) {
                List<String> productName = listMap.get("productName");
                List<String> productType = listMap.get("productType");
                List<String> productDescribe = listMap.get("productDescribe");
                List<String> skuAttrValueNames = listMap.get("skuAttrValueNames");

                if (!StringUtils.isEmpty(productName)) {
                    product.setProductName(productName.get(ProductConstant.ZERO));
                }
                if (!StringUtils.isEmpty(productType)) {
                    product.setProductType(productType.get(ProductConstant.ZERO));
                }
                if (!StringUtils.isEmpty(productDescribe)) {
                    product.setProductDescribe(productDescribe.get(ProductConstant.ZERO));
                }
                if (!StringUtils.isEmpty(skuAttrValueNames)) {
                    product.setSkuAttrValueNames(skuAttrValueNames.get(ProductConstant.ZERO));
                }
            }
        });
        //总条数 / 每页条数
        Double numFound = (double) queryResponse.getResults().getNumFound();
        long pages = (long) Math.ceil(numFound / Double.valueOf(productDTO.getPageSize()));

        //如果用户等录判断是否点赞
        if (null != productDTO.getCurrentlyLoggedInUserId()) {
            QueryWrapper<ProductLikeEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", productDTO.getCurrentlyLoggedInUserId());
            queryWrapper.in("product_id", productIds);
            List<ProductLikeEntity> likeList = productLikeService.list(queryWrapper);
            for (ProductLikeEntity like : likeList) {
                for (ProductSolr product : productSolrList) {
                    if (like.getProductId().equals(Integer.valueOf(product.getProductId()))) {
                        product.setIsLike(like.getIsLike());
                        break;
                    }
                }
            }
        }
        return new PageResult().toBuilder().pages(pages).list(DozerUtil.mapList(productSolrList, ProductDTO.class)).build();
    }

    /**
     * 导入
     * @param bytes 字节流
     * @param userId 用户id
     * @param fileName 文件名
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public void importProduct(byte[] bytes, Integer userId, String fileName) throws Exception, BusinessException {
        Map<String, String> productTypeMap = new HashMap<>();
        productTypeMap.put("手机", "手机");
        productTypeMap.put("电脑", "电脑");
        productTypeMap.put("衣服", "衣服");
        productTypeMap.put("图书", "图书");
        productTypeMap.put("食品", "食品");
        productTypeMap.put("数码产品", "数码产品");
        // 解析表格
        Workbook workBook = POIUtil.getExcelWorkBook(new ByteArrayInputStream(bytes), fileName);
        // 获取表格总页数
        int sheets = workBook.getNumberOfSheets();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (int i = 0; i < sheets; i++) {
            Sheet sheet = workBook.getSheetAt(i);
            productTypeMap.forEach((name, code) -> {
                String sheetName = sheet.getSheetName();
                // 等于页名 就使用当前的code
                if (sheetName.equals(name)) {
                    int rows = sheet.getPhysicalNumberOfRows();
                    for (int j = 1; j < rows; j++) {
                        Row row = sheet.getRow(j);
                        // 获取参数
                        String productName = POIUtil.getStringCellValue(row.getCell(0));
                        String productDescribe = POIUtil.getStringCellValue(row.getCell(1));
                        String skuInfo = POIUtil.getStringCellValue(row.getCell(2));
                        Integer skuCount = Integer.valueOf(POIUtil.getStringCellValue(row.getCell(3)));
                        BigDecimal skuPrice = new BigDecimal(POIUtil.getStringCellValue(row.getCell(4)));
                        Integer skuRate = Integer.valueOf(POIUtil.getStringCellValue(row.getCell(5)));
                        // if (!StringUtils.hasText(productName) || !StringUtils.hasText(productDetail)) 等等
                        ProductDTO productDTO = new ProductDTO();
                        productDTO.setProductName(productName);
                        productDTO.setProductDescribe(productDescribe);
                        productDTO.setSkuAttrValueNames(skuInfo);
                        productDTO.setProductType(code);
                        productDTO.setSkuCount(skuCount);
                        productDTO.setSkuPrice(skuPrice);
                        productDTO.setSkuRate(skuRate);
                        productDTOList.add(productDTO);
                    }
                }
            });
        }
        if (productDTOList.size() == 0) {
            throw new BusinessException("禁止传入空数据表格");
        }
        Map<String, List<ProductDTO>> productMap = productDTOList.stream().collect(Collectors.groupingBy(ProductDTO::getProductName));
        productMap.forEach((productName, skuList) -> {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setUserId(userId);
            productEntity.setProductName(productName);
            productEntity.setProductDescribe(skuList.get(0).getProductDescribe());
            productEntity.setCreateTime(LocalDateTime.now());
            productEntity.setProductType(skuList.get(0).getProductType());
            productEntity.setProductStatus(DictConstant.PRODUCT_STATUS_UP);
            this.save(productEntity);
            List<SkuEntity> productSkuList = new ArrayList<>();
            // 遍历拼接sku数据
            for (ProductDTO productDTO : skuList) {
                SkuEntity skuEntity = new SkuEntity();
                skuEntity.setProductId(productEntity.getId());
                skuEntity.setSkuCount(productDTO.getSkuCount());
                skuEntity.setSkuPrice(productDTO.getSkuPrice());
                skuEntity.setSkuRate(String.valueOf(productDTO.getSkuRate()));
                skuEntity.setIsDefault(DictConstant.NOT_DEFAULT);
                skuEntity.setSkuAttrIds("-");
                skuEntity.setSkuAttrNames("-");
                skuEntity.setSkuAttrValueIds("-");
                skuEntity.setSkuAttrValueNames(productDTO.getSkuAttrValueNames());
                skuEntity.setSkuStatus(DictConstant.PRODUCT_STATUS_UP);
                productSkuList.add(skuEntity);
            }
            productSkuList.get(0).setIsDefault("IS_DEFAULT");
            skuService.saveBatch(productSkuList);
        });
        /*Workbook workbook;
        if (fileName.endsWith(".xls")) {
            //2003 转换stream
            workbook = new HSSFWorkbook(new ByteArrayInputStream(bytes));
        } else {
            //2007 转换stream
            workbook =new XSSFWorkbook(new ByteArrayInputStream(bytes));
        }
        //商品编号	名称	邮费	描述	图片	商品类型
        Sheet productSheet = workbook.getSheetAt(0);
        int productRows = productSheet.getPhysicalNumberOfRows();
        List<ProductDTO> productList = new ArrayList<>(productRows);
        for (int i = 1; i < productRows; i++) {
            Row row = productSheet.getRow(i);
            ProductDTO productEntity = new ProductDTO();
            productEntity.setProductName(row.getCell(1).getStringCellValue());
            productEntity.setFreightId(Integer.valueOf(row.getCell(2).getStringCellValue()));
            productEntity.setProductDescribe(row.getCell(3).getStringCellValue());
            productEntity.setProductImg(row.getCell(4).getStringCellValue());
            productEntity.setProductType(row.getCell(5).getStringCellValue().split("-")[0]);
            productEntity.setUserId(userId);
            productEntity.setProductStatus(DictConstant.PRODUCT_STATUS_UP);
            productEntity.setCreateTime(LocalDateTime.now());
            productEntity.setProductNo((int) row.getCell(0).getNumericCellValue());
            productList.add(productEntity);
        }
        List<ProductEntity> entityList = DozerUtil.mapList(productList, ProductEntity.class);
        saveBatch(entityList);
        //遍历 将id赋值给productDTO
        for (int i = 0; i < entityList.size(); i++) {
            productList.get(i).setProductId(entityList.get(i).getId());
        }
        //商品编号	价格	库存	折扣（百分比）	属性名（使用：分割）	属性值（使用：分割）
        Sheet skuSheet = workbook.getSheetAt(1);
        int skuRows = skuSheet.getPhysicalNumberOfRows();
        List<SkuDTO> skuList = new ArrayList<>(skuRows);
        for (int i = 1; i < skuRows; i++) {
            Row row = skuSheet.getRow(i);
            SkuDTO skuDTOReq = new SkuDTO();
            skuDTOReq.setSkuPrice(new BigDecimal(row.getCell(1).getNumericCellValue()));
            skuDTOReq.setSkuCount((int) row.getCell(2).getNumericCellValue());
            skuDTOReq.setSkuRate((int) row.getCell(3).getNumericCellValue());
            skuDTOReq.setSkuStatus(DictConstant.PRODUCT_STATUS_UP);
            skuDTOReq.setSkuAttrIds(ProductConstant.LOSE_ONE);
            skuDTOReq.setSkuAttrNames(row.getCell(4).getStringCellValue());
            skuDTOReq.setSkuAttrValueIds(ProductConstant.LOSE_ONE);
            skuDTOReq.setSkuAttrValueNames(row.getCell(5).getStringCellValue());
            skuDTOReq.setIsDefault(DictConstant.NOT_DEFAULT);
            skuDTOReq.setProductNo((int) row.getCell(0).getNumericCellValue());
            skuList.add(skuDTOReq);
        }
        //为了设置productId
        for (SkuDTO skuDTO : skuList) {
            for (ProductDTO productDTO : productList) {
                if (skuDTO.getProductNo().equals(productDTO.getProductNo())) {
                    skuDTO.setProductId(productDTO.getProductId());
                    break;
                }
            }
        }
        //为了设置默认sku
        Map<Integer, List<SkuDTO>> collect = skuList.stream().collect(Collectors.groupingBy(SkuDTO::getProductNo));
        for (Map.Entry<Integer, List<SkuDTO>> entry : collect.entrySet()) {
            entry.getValue().get(0).setIsDefault(DictConstant.HAVE_DEFAULT);
        }
        skuService.saveBatch(DozerUtil.mapList(skuList, SkuEntity.class));*/
    }

    /**
     * 用户点赞
     * @param productId 商品id
     * @param isLike 是否点赞 0 是 1 否
     * @param userId 用户id
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public void like(Integer productId, Integer isLike, Integer userId) throws Exception, BusinessException {
        //根据商品id和用户id去查商品点赞表
        ProductLikeEntity productLikeEntity = productLikeService.getOne(new QueryWrapper<ProductLikeEntity>().eq("product_id", productId).eq("user_id", userId));
        //存在修改 不存在新增
        if (null != productLikeEntity) {
            productLikeEntity.setIsLike(isLike);
            productLikeService.updateById(productLikeEntity);
        } else {
            productLikeEntity = new ProductLikeEntity().setProductId(productId).setUserId(userId).setIsLike(isLike);
            productLikeService.save(productLikeEntity);
        }
        //修改商品点赞数量 得到商品原点赞量
        ProductEntity productEntity = this.getById(productId);
        Integer praiseNumber = productEntity.getPraiseNumber();
        //点赞状态为 0 已点赞 进行追加 反之追减
        if (isLike.equals(ProductConstant.IS_LIKE)) {
            praiseNumber++;
        } else {
            praiseNumber--;
        }
        productEntity.setPraiseNumber(praiseNumber);
        this.updateById(productEntity);
        //solr更新
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("id:"+ productId);
        QueryResponse response = solrClient.query(solrQuery);
        ProductSolr productSolr = response.getBeans(ProductSolr.class).get(ProductConstant.ZERO);
        productSolr.setPraiseNumber(praiseNumber);
        solrClient.addBean(productSolr);
        solrClient.commit();
    }

    /**
     * 新增评论
     * @param productReviewsDTO 评论dto
     * @param userId 评论人id
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public void addComment(ProductReviewsDTO productReviewsDTO, Integer userId) throws Exception, BusinessException {
        //遍历评论集合 进行赋值操作 创建时间 回复人id 顶级评论id（-1）
        List<ProductReviewsDTO> reviewsList = productReviewsDTO.getReviewsList();
        List<Integer> detailIdList = new ArrayList<>();
        List<Integer> productIdList = new ArrayList<>();
        reviewsList.forEach(reviews -> {
            if (StringUtils.isEmpty(reviews.getComment())) {
                throw new BusinessException("请一次性填写完所有评论内容，再进行提交");
            }
            reviews.setCreateTime(LocalDateTime.now()).setReviewerId(userId).setReplyId(ProductConstant.REPLY_PARENT);
            detailIdList.add(reviews.getDetailId());
            productIdList.add(reviews.getProductId());
        });
        productReviewsService.saveBatch(DozerUtil.mapList(reviewsList, ProductReviewsEntity.class));

        //修改明细订单评论状态
        orderApi.updateDetailComment(detailIdList, OrderConstant.IS_COMMENT);

        //判断该订单下所有商品是否全部已评论，是的话修改状态为已完成
        Integer isAllComment = orderApi.findAllIsCommentByChildOrderNo(productReviewsDTO.getOrderNo());
        if (ProductConstant.ZERO.equals(isAllComment)) {
            orderApi.updateStatus(productReviewsDTO.getOrderNo(), DictConstant.COMPLETED, null);
        }

        //修改商品表的评论数量
        List<ProductEntity> products = this.listByIds(productIdList);
        products.forEach(productEntity -> productEntity.setReviewsNumber(productEntity.getReviewsNumber() + ProductConstant.REVIEWS_ONE));
        updateBatchById(products);
    }

    /**
     * 根据商品id查找全部商品评论
     * @param productReviewsDTO 商品评论
     * @return List<ProductReviewsDTO>
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public PageResult findReviewsByProductId(ProductReviewsDTO productReviewsDTO) throws Exception, BusinessException {
        Page<ProductReviewsEntity> page = new Page<>(productReviewsDTO.getPageNo(), productReviewsDTO.getPageSize());
        IPage<ProductReviewsBO> iPage =  baseMapper.findReviewsByProductId(page, DozerUtil.map(productReviewsDTO, ProductReviewsBO.class));
        //得到评论全部数据
        List<ProductReviewsDTO> parentReviewList = DozerUtil.mapList(iPage.getRecords(), ProductReviewsDTO.class);
        List<Integer> idList = parentReviewList.stream().map(ProductReviewsDTO::getId).collect(Collectors.toList());
        //判断id集合长度大于0
        if (idList.size() > ProductConstant.ZERO) {
            //根据回复id查找评论数据
            List<ProductReviewsDTO> replyList = productReviewsService.findReviewsByReplyId(idList);
            //顶级评论下的所有回复
            parentReviewList.forEach(reviews ->{
                List<ProductReviewsDTO> childReviewList = new ArrayList<>();
                findChildReviews(reviews, replyList, childReviewList);
                reviews.setReplyList(childReviewList);
            });
        }
        return new PageResult().toBuilder().pages(iPage.getPages()).list(parentReviewList).build();
    }

    /**
     * 评论下的所有子评论
     * @param parentReviews 父级评论
     * @param allChildReviewList 全部子评论集合
     * @param childReviewList 对应子评论集合
     */
    private void findChildReviews(ProductReviewsDTO parentReviews, List<ProductReviewsDTO> allChildReviewList, List<ProductReviewsDTO> childReviewList) {
        //遍历全部子评论集合 如果子评论回复id和父级评论id相等进行赋值操作
        allChildReviewList.forEach( childReviews -> {
            if (childReviews.getReplyId().equals(parentReviews.getId())) {
                childReviewList.add(childReviews);
                findChildReviews(childReviews, allChildReviewList, childReviewList);
            }
        });
    }

    /**
     * 新增回复
     * @param productReviewsDTO 商品评论
     * @param userId 商家id
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public void addReply(ProductReviewsDTO productReviewsDTO, Integer userId) throws Exception, BusinessException {
        //根据父级id查是否已有回复
        ProductReviewsEntity replyExist = productReviewsService.getOne(new QueryWrapper<ProductReviewsEntity>().eq("reply_id", productReviewsDTO.getReplyId()));
        //有回复的话不能再次回复
        if (replyExist != null) {
            throw new BusinessException("已有回复~，不能重复添加");
        }
        //根据id查数据
        ProductReviewsEntity parentReview = productReviewsService.getOne(new QueryWrapper<ProductReviewsEntity>().eq("id", productReviewsDTO.getReplyId()));
        ProductEntity product = this.getOne(new QueryWrapper<ProductEntity>().eq("id", parentReview.getProductId()));
        //不是商家无权限评论
        if (!product.getUserId().equals(userId)) {
            throw new BusinessException("您暂无权限对此评论进行回复~");
        }
        productReviewsDTO.setReviewerId(userId).setProductId(product.getUserId()).setCreateTime(LocalDateTime.now());
        productReviewsService.save(DozerUtil.map(productReviewsDTO, ProductReviewsEntity.class));
    }

    /**
     * 好评率
     * @param id 商品id
     * @return Integer
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public Integer findGoodRateByProductId(Integer id) throws Exception, BusinessException {
        //获取好评率 好评率= 好评数/总评论数 * 100%
        return baseMapper.findGoodRateByProductId(id);
    }


}
