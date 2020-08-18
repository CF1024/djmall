/*
 * 作者：CF
 * 日期：2020-07-29 19:07
 * 项目：djmall
 * 模块：djmall_platform
 * 类名：OrderDetailVoResp
 * 版权所有(C), 2020. 所有权利保留
 */

/*
 * 作者：CF
 * 日期：2020-07-29 18:06
 * 项目：djmall
 * 模块：djmall_platform
 * 类名：OrderDetailVoResp
 * 版权所有(C), 2020. 所有权利保留
 */

/*
 * 作者：CF
 * 日期：2020-06-09 10:23
 * 项目：djmall
 * 模块：djmall_order_provider
 * 类名：OrderDetailEntity
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.platform.vo.order;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author chengf
 * @date 2020/7/29 17:47
 * 订单明细
 */
@Data
@Accessors(chain = true)
public class OrderDetailVoResp implements Serializable {

    /**
     * 订单详情表id
     */
    private Integer id;

    /**
     * 子订单号
     */
    private String childOrderNo;

    /**
     * 父订单号
     */
    private String parentOrderNo;

    /**
     * 购买人id
     */
    private Integer buyerId;

    /**
     * 商家id
     */
    private Integer businessId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * SkuID-针对再次购买时使用
     */
    private Integer skuId;

    /**
     * SKU信息(iphone-红色-64G)
     */
    private String skuInfo;

    /**
     * SKU价格
     */
    private BigDecimal skuPrice;

    /**
     * SKU折扣
     */
    private Integer skuRate;

    /**
     * 购买数量
     */
    private Integer buyCount;

    /**
     * 支付金额（含运费）
     */
    private BigDecimal payMoney;

    /**
     * 运费
     */
    private BigDecimal skuFreight;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 商品名
     */
    private String productName;

    /**
     * 是否已评论
     */
    private Integer isComment;
}
