/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_product_api
 * 类名：ProductDTO
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.product.dto.spu;

import com.dj.mall.product.dto.sku.SkuDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

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
public class ProductDTO implements Serializable {
    /**
     * 当前登录人
     */
    private Integer currentlyLoggedInUserId;
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
     * 好评率
     */
    private Integer goodRate;
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
     * 公司 展示商品
     */
    private String company;
    /**
     * 运费 展示商品
     */
    private BigDecimal freight;
    /**
     * 生成sku集合 新增商品
     */
    private List<SkuDTO> productSkuList;
    /**
     * 删除商品照片 修改商品
     */
    private String removeImg;
    /**
     * 最低价格 展示 价格查询
     */
    private BigDecimal skuPriceMin;
    /**
     * 最高价格 展示 价格查询
     */
    private BigDecimal skuPriceMax;
    /**
     * 是否默认：是 展示条件
     */
    private String isDefault;
    /**
     * 关键字
     */
    private String productKeywords;

    /**
     * 商品SKU ID
     */
    private Integer skuId;
    /**
     * SKU价格
     */
    private BigDecimal skuPrice;
    /**
     * sku库存
     */
    private Integer skuCount;
    /**
     * SKU折扣
     */
    private Integer skuRate;
    /**
     * SKU属性值名称
     */
    private String skuAttrValueNames;
    /**
     * 是否点赞 0 是 1 否
     */
    private Integer isLike;
}
