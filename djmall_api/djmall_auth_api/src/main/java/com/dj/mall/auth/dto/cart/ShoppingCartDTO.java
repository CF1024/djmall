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
import java.math.BigDecimal;
import java.util.List;

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
     * 是否被选中 0：是 1：否  2立即购买
     */
    private Integer checked;

    /*====================================================*/
    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品价格
     */
    private BigDecimal skuPrice;
    /**
     * 商品属性名
     */
    private String skuAttrValueNames;

    /**
     * 折扣
     */
    private String skuRate;

    /**
     * 库存
     */
    private Integer skuCount;
    /**
     * 运费展示
     */
    private BigDecimal freight;
    /**
     * 购物车id集合
     */
    private List<Integer> cartIdList;
    /**
     * 折后价
     */
    private BigDecimal discountPrice;
    /**
     * 总价
     */
    private BigDecimal totalPrice;
    /**
     * 总折后价
     */
    private BigDecimal totalDiscountedPrice;
    /**
     * 总运费
     */
    private BigDecimal totalFreight;
    /**
     * 最终价格
     */
    private BigDecimal finalPrice;

}
