package com.dj.mall.admin.vo.dict.sku;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chengf
 * @date 2020/6/28 17:42
 * 通用SKU维护
 */
@Data
@Builder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
public class SkuGmVOResp implements Serializable {
    /**
     * skuGmId
     */
    private Integer skuGmId;
    /**
     * 商品类型
     */
    private String productType;
    /**
     * 商品属性ID
     */
    private Integer attrId;
    /**
     * 商品属性名
     */
    private String attrName;
}
