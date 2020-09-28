/*
 * 作者：CF
 * 日期：2020-07-29 19:07
 * 项目：djmall
 * 模块：djmall_platform
 * 类名：OrderInfoVoReq
 * 版权所有(C), 2020. 所有权利保留
 */

/*
 * 作者：CF
 * 日期：2020-07-29 18:06
 * 项目：djmall
 * 模块：djmall_platform
 * 类名：OrderInfoVoReq
 * 版权所有(C), 2020. 所有权利保留
 */

/*
 * 作者：CF
 * 日期：2020-06-19 11:34
 * 项目：djmall
 * 模块：djmall_order_provider
 * 类名：OrderInfoEntity
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.order.bo;

import com.dj.mall.order.entity.OrderDetailEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chengf
 * @date 2020/7/29 17:54
 * 订单子表
 */
@Data
@Accessors(chain = true)
public class OrderInfoBO implements Serializable {
    /**
     * 全部评论 条
     */
    private Integer isAllComment;
    /**
     * 商户昵称
     */
    private String businessNickNameShow;
    /**
     * 购买人昵称
     */
    private String nickNameShow;
    /**
     * 购买人手机号
     */
    private String userPhoneShow;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 商品名
     */
    private String productName;
    /**
     * 商品详情集合
     */
    private List<OrderDetailEntity> detailList;
    /**
     * 商品名
     */
    private String productNameShow;

    /**
     * 支付类型
     */
    private String payTypeShow;

    /**
     * 折扣
     */
    private Integer skuRateShow;

    /**
     * 订单状态
     */
    private String orderStatusShow;
    /**
     * 子订单号
     */
    private String orderNo;

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
     * 提醒状态
     */
    private Integer remindStatus;

    /**
     * 提醒时间
     */
    private LocalDateTime remindTime;
    /**
     * 支付宝交易号
     */
    private String alipayTradeNo;
}
