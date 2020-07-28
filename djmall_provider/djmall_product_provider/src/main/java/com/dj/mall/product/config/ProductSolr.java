/*
 * 作者：CF
 * 日期：2020-07-23 11:12
 * 项目：djmall
 * 模块：djmall_product_provider
 * 类名：ProductSolr
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.product.config;

import com.dj.mall.product.dto.sku.SkuDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductSolr implements Serializable {
    /**
     * 商品spu的id
     */
    @Field(value = "id")
    private String productId;
    /**
     *商品名
     */
    @Field(value = "product_name")
    private String productName;
    /**
     *运费id
     */
    @Field
    private Integer freightId;
    /**
     *商品描述
     */
    @Field(value = "product_describe")
    private String productDescribe;
    /**
     * 商品照片
     */
    @Field(value = "product_img")
    private String productImg;
    /**
     * 商品类型
     */
    @Field(value = "product_type")
    private String productType;
    /**
     * 用户id
     */
    @Field
    private Integer userId;
    /**
     * 商品状态
     */
    @Field(value = "product_status")
    private String productStatus;
    /**
     *点赞量
     */
    @Field(value = "praise_number")
    private Integer praiseNumber;
    /**
     * 商品类型集合 展示商品 复选框查询
     */
    private List<String> productTypeList;
    /**
     * 公司 展示商品
     */
    @Field
    private String company;
    /**
     * 运费 展示商品
     */
    @Field(value = "freight_show")
    private Double freightShow;

    /*=========================商城========================*/
    /**
     * 最低价格 展示 价格查询
     */
    @Field
    private Double skuPriceMin;
    /**
     * 最高价格 展示 价格查询
     */
    @Field
    private Double skuPriceMax;
    /**
     * 库存 展示
     */
    @Field(value = "sku_count_show")
    private Integer skuCountShow;
    /**
     * 价格 展示
     */
    @Field(value = "sku_price_show")
    private Double skuPriceShow;
    /**
     *  折扣 展示
     */
    @Field(value = "sku_rate_show")
    private String skuRateShow;
    /**
     *sku属性值名集合
     */
    @Field(value = "sku_attr_value_names")
    private String skuAttrValueNames;
    /**
     *  商品查询 关键字
     */
    @Field(value = "product_keywords")
    private String productKeywords;

}
