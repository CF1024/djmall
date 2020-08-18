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
    @Field
    private String productName;
    /**
     *运费id
     */
    @Field
    private Integer freightId;
    /**
     *商品描述
     */
    @Field
    private String productDescribe;
    /**
     * 商品照片
     */
    @Field
    private String productImg;
    /**
     * 商品类型
     */
    @Field
    private String productType;
    /**
     * 用户id
     */
    @Field
    private Integer userId;
    /**
     * 商品状态
     */
    @Field
    private String productStatus;
    /**
     * 订单量
     */
    @Field
    private Integer orderNumber;
    /**
     *点赞量
     */
    @Field
    private Integer praiseNumber;
    /**
     * 公司 展示商品
     */
    @Field
    private String company;
    /**
     * 运费 展示商品
     */
    @Field
    private Double freight;
    /**
     * 库存 展示
     */
    @Field
    private Integer skuCount;
    /**
     * 价格 展示
     */
    @Field
    private Double skuPrice;
    /**
     *  折扣 展示
     */
    @Field
    private String skuRate;
    /**
     *sku属性值名集合
     */
    @Field
    private String skuAttrValueNames;
    /**
     *  商品查询 关键字
     */
    @Field
    private String productKeywords;
    /**
     * 评论量
     */
    @Field
    private Integer commentsNum;

    /**
     * 好评率
     */
    @Field
    private Integer goodRate;

    /**
     * 商品类型集合 展示商品 复选框查询
     */
    private List<String> productTypeList;
    /**
     * 最低价格 展示 价格查询
     */
    private Double skuPriceMin;
    /**
     * 最高价格 展示 价格查询
     */
    private Double skuPriceMax;
    /**
     * 是否点赞 0 是 1 否
     */
    private Integer isLike;
}
