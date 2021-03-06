/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_product_api
 * 类名：ProductApi
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.product.api.spu;

import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.product.dto.reviews.ProductReviewsDTO;
import com.dj.mall.product.dto.sku.SkuDTO;
import com.dj.mall.product.dto.spu.ProductDTO;

import java.util.List;

/**
 * @author chengf
 * @date 2020/7/2 15:16
 * 商品Api
 */
public interface ProductApi {
    /**
     * 商品展示
     * @param productDTO 商品dto
     * @return PageResult
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    PageResult findAll(ProductDTO productDTO) throws Exception, BusinessException;

    /**
     * 去重
     * @param productDTO 商品Dto
     * @return Boolean
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    Boolean deDuplicate(ProductDTO productDTO) throws Exception, BusinessException;

    /**
     * 商品新增
     * @param productDTO 商品Dto
     * @param file byte流文件
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    void addProduct(ProductDTO productDTO, byte[] file) throws Exception, BusinessException;

    /**
     * 根据id修改商品和商品sku上下架状态
     * @param id 商品ID
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    void updateProductStatusById(Integer id) throws Exception, BusinessException;

    /**
     * 根据id查商品信息
     * @param productId 商品ID
     * @return ProductDTO
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    ProductDTO findProductById(Integer productId) throws Exception, BusinessException;

    /**
     * 根据id修改商品
     * @param productDTO productDTO
     * @param file 图片文件流
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    void updateProductById(ProductDTO productDTO, byte[] file) throws Exception, BusinessException;


    /**
     * 增量索引
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    void incrementalIndex() throws Exception, BusinessException;

    /**
     * 重构索引
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    void refactoringTheIndex() throws Exception, BusinessException;

    /*=========================商城========================*/

    /**
     * 商城展示
     * @param productDTO  productDTO
     * @return PageResult
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    PageResult findList(ProductDTO productDTO) throws Exception, BusinessException;

    /**
     * 导入
     * @param bytes 字节流
     * @param userId 用户id
     * @param fileName 文件名
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    void importProduct(byte[] bytes, Integer userId, String fileName) throws Exception, BusinessException;

    /**
     * 用户点赞
     * @param productId 商品id
     * @param isLike 是否点赞 0 是 1 否
     * @param userId 用户id
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    void like(Integer productId, Integer isLike, Integer userId) throws Exception, BusinessException;

    /**
     * 新增评论
     * @param productReviewsDTO 评论dto
     * @param userId 评论人id
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    void addComment(ProductReviewsDTO productReviewsDTO, Integer userId) throws Exception, BusinessException;

    /**
     * 根据商品id查找全部商品评论
     * @param productReviewsDTO 商品评论
     * @return List<ProductReviewsDTO>
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    PageResult findReviewsByProductId(ProductReviewsDTO productReviewsDTO) throws Exception, BusinessException;

    /**
     * 新增回复
     * @param productReviewsDTO 商品评论
     * @param userId 商家id
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    void addReply(ProductReviewsDTO productReviewsDTO, Integer userId) throws Exception, BusinessException;

    /**
     * 好评率
     * @param id 商品id
     * @return Integer
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    Integer findGoodRateByProductId(Integer id) throws Exception, BusinessException;
}
