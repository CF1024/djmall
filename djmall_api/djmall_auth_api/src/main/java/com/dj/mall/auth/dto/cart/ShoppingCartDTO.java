/*
 * 作者：CF
 * 日期：2020-07-16 10:36
 * 项目：djmall
 * 模块：djmall_auth_provider
 * 类名：CartEntity
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chengf
 * @date 2020/7/16 10:36
 * 购物车实体类
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO implements Serializable {
    /**
     * 购物车id
     */
    private Integer cartId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * sku id
     */
    private Integer skuId;
    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 是否被选中 0：是 1：否
     */
    private Integer checked;

}
