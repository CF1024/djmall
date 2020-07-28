/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：ProductVOReq
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.platform.vo.product.spu;

import com.dj.mall.platform.vo.product.sku.SkuVOReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chengf
 * @date 2020/7/2 11:47
 * 商品spu
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductVOReq implements Serializable {
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
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     *修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 当前页
     */
    private Integer pageNo = 1;
    /**
     * 每页条数
     */
    private Integer pageSize = 5;
    /**
     * 商品类型集合 展示商品 复选框查询
     */
    private List<String> productTypeList;
    /**
     * 生成sku集合 新增商品
     */
    private List<SkuVOReq> productSkuList;
    /**
     * 删除商品照片 修改商品
     */
    private String removeImg;

    /*=========================商城========================*/
    /**
     * 最低价格 展示 价格查询
     */
    private BigDecimal skuPriceMin;
    /**
     * 最高价格 展示 价格查询
     */
    private BigDecimal skuPriceMax;
    /**
     * 关键字
     */
    private String productKeywords;
}
