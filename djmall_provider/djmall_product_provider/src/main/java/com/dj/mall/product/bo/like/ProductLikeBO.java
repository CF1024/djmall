/*
 * 作者：CF
 * 日期：2020-06-04 21:38
 * 项目：djmall
 * 模块：djmall_product_provider
 * 类名：ProductLikeEntity
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.product.bo.like;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author chengf
 * @date 2020/8/14 14:53
 * 点赞表
 */
@Data
@Accessors(chain = true)
public class ProductLikeBO implements Serializable {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 点赞人 id
     */
    private Integer userId;

    /**
     * 是否点赞 0：是  1：否
     */
    private Integer isLike;
}
