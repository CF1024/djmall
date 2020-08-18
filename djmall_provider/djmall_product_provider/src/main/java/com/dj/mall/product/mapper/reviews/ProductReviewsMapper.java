/*
 * 作者：CF
 * 日期：2020-08-14 20:35
 * 项目：djmall
 * 模块：djmall_product_provider
 * 类名：ProductReviewsMapper
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.product.mapper.reviews;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.product.bo.reviews.ProductReviewsBO;
import com.dj.mall.product.entity.reviews.ProductReviewsEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author chengf
 * @date 2020/8/14 20:36
 * 评论mapper接口
 */
public interface ProductReviewsMapper extends BaseMapper<ProductReviewsEntity> {
    /**
     * 根据回复id查评论集合
     * @param idList 回复id集合
     * @return  List<ProductReviewsBO>
     * @throws DataAccessException 异常
     */
    List<ProductReviewsBO> findReviewsByReplyId(List<Integer> idList) throws DataAccessException;
}
