/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_product_provider
 * 类名：ProductMapper
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.product.mapper.spu;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.mall.product.bo.reviews.ProductReviewsBO;
import com.dj.mall.product.bo.spu.ProductBO;
import com.dj.mall.product.entity.reviews.ProductReviewsEntity;
import com.dj.mall.product.entity.spu.ProductEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author chengf
 * @date 2020/7/2 15:16
 * 商品mapper
 */
public interface ProductMapper extends BaseMapper<ProductEntity> {
    /**
     * 商品展示
     * @param page 分页
     * @param productBO 商品bo
     * @return  IPage<ProductBO>
     * @throws DataAccessException 数据访问异常
     */
    IPage<ProductBO> findAll(@Param("page") Page<?> page, @Param("product") ProductBO productBO) throws DataAccessException;

    /**
     * 根据商品id查找全部商品评论
     * @param page 分页
     * @param productReviewsBO 商品评论bo
     * @return Page<ProductReviewsEntity>
     * @throws DataAccessException 数据访问异常
     */
    IPage<ProductReviewsBO> findReviewsByProductId(@Param("page") Page<ProductReviewsEntity> page, @Param("reviews") ProductReviewsBO productReviewsBO) throws DataAccessException;

    /**
     * 计算好评率
     * @param productId 商品id
     * @return Integer
     * @throws DataAccessException 数据访问异常
     */
    Integer findGoodRateByProductId(Integer productId) throws DataAccessException;
}
