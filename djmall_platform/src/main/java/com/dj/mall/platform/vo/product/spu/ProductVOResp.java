/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：ProductVOResp
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.platform.vo.product.spu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author chengf
 * @date 2020/7/2 11:47
 * 商品spu
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductVOResp implements Serializable {
    /**
     * 商品spu的id
     */
    private Integer productId;
    /**
     *商品名
     */
    private String productName;
    /**
     *运费id
     */
    private Integer freightId;
    /**
     *商品描述
     */
    private String productDescribe;
    /**
     * 商品照片
     */
    private String productImg;
    /**
     * 商品类型
     */
    private String productType;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 商品状态
     */
    private String productStatus;
    /**
     * 订单量
     */
    private Integer orderNumber;
    /**
     *点赞量
     */
    private Integer praiseNumber;
    /**
     *评论量
     */
    private Integer reviewsNumber;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     *修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 公司
     */
    private String company;
    /**
     * 运费
     */
    private BigDecimal freight;

    /*=========================商城========================*/
    /**
     * 库存 展示
     */
    private Integer skuCount;
    /**
     * 价格 展示
     */
    private BigDecimal skuPrice;
    /**
     *  折扣 展示
     */
    private String skuRate;
    /**
     * 是否点赞 0 是 1 否
     */
    private Integer isLike;
}
