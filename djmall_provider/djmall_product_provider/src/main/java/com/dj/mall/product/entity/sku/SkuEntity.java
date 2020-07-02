package com.dj.mall.product.entity.sku;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chengf
 * @date 2020/7/2 11:47
 * 商品sku实体类
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("djmall_product_sku")
public class SkuEntity implements Serializable {
    /**
     * 商品sku的id
     */
    @TableId(type = IdType.AUTO)
    @Mapping("skuId")
    private Integer id;
    /**
     *商品ID
     */
    private Integer productId;
    /**
     *SKU价格
     */
    private BigDecimal skuPrice;
    /**
     *sku库存
     */
    private Integer skuCount;
    /**
     * sku折扣
     */
    private String skuRate;
    /**
     * sku状态 下架 上架
     */
    private String skuStatus;
    /**
     * sku属性ID集合 -1 代表自定义
     */
    private String skuAttrIds;
    /**
     * sku属性名集合
     */
    private String skuAttrNames;
    /**
     * sku属性值ID集合 -1 代表自定义
     */
    private String skuAttrValueIds;
    /**
     *sku属性值名集合
     */
    private String skuAttrValueNames;
    /**
     *是否默认
     */
    private String isDefault;
}
