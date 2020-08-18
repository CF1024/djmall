/*
 * 作者：CF
 * 日期：2020-07-29 19:07
 * 项目：djmall
 * 模块：djmall_platform
 * 类名：OrderVoReq
 * 版权所有(C), 2020. 所有权利保留
 */

/*
 * 作者：CF
 * 日期：2020-07-29 18:06
 * 项目：djmall
 * 模块：djmall_platform
 * 类名：OrderVoReq
 * 版权所有(C), 2020. 所有权利保留
 */

/*
 * 作者：CF
 * 日期：2020-06-28 15:47
 * 项目：djmall
 * 模块：djmall_order_provider
 * 类名：OrderEntity
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.platform.vo.order;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chengf
 * @date 2020/7/29 17:44
 * 订单主表
 */
@Data
@Accessors(chain = true)
public class OrderVoReq implements Serializable {
    /**
     * 当前页
     */
    private Integer pageNo = 1;
    /**
     * 每页条数
     */
    private Integer pageSize = 5;
    /**
     * sku id集合
     */
    private List<Integer> skuIdList;

    /**
     * 购物车id集合
     */
    private List<Integer> cartIdList;

    /**
     * 用户地址id
     */
    private Integer addressId;

    /**
     * 购买数量集合
     */
    private List<Integer> quantityList;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 购买人id
     */
    private Integer buyerId;

    /**
     * 总金额
     */
    private BigDecimal totalMoney = BigDecimal.ZERO;

    /**
     * 实际支付总金额
     */
    private BigDecimal totalPayMoney = BigDecimal.ZERO;

    /**
     * 总运费
     */
    private BigDecimal totalFreight = BigDecimal.ZERO;

    /**
     * 购买物品总数量
     */
    private Integer totalBuyCount = 0;

    /**
     * 支付方式
     */
    private String payType;

    /**
     * 收货信息-省
     */
    private String receiverProvince;

    /**
     * 收货信息-市
     */
    private String receiverCity;

    /**
     * 收货信息-县区
     */
    private String receiverCounty;

    /**
     * 收货信息-收货人姓名
     */
    private String receiverName;

    /**
     * 收货信息-收货人手机号码
     */
    private String receiverPhone;

    /**
     * 收货信息-详细地址
     */
    private String receiverDetail;

    /**
     * 订单状态:[已取消/待支付/待发货/已发货/确认收货/已完成]
     */
    private String orderStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 支付宝交易号
     */
    private String aLiPayTradeNo;

}
