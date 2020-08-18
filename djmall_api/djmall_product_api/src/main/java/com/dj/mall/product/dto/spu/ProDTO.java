package com.dj.mall.product.dto.spu;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Description 商品 DTO
 * @Author zxy
 */
@Data
@Accessors(chain = true)
public class ProDTO implements Serializable {

    /**
     * 商品ID
     */
    private Integer productId;
    /**
     * 商品SKU ID
     */
    private Integer skuId;
    /**
     * 商品名
     */
    private String productName;
    /**
     * 商品类型
     */
    private String productType;
    /**
     * 运费ID
     */
    private Integer freightId;
    /**
     * 商品描述
     */
    private String productDescribe;
    /**
     * 图片
     */
    private String productImg;
    /**
     * 商户ID
     */
    private Integer userId;
    /**
     * 商品状态
     */
    private String productStatus;
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
     * 商品类型集合
     */
    List<String> productTypes;
}
