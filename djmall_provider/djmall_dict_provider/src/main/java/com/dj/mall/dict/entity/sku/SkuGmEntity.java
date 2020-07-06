/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_dict_provider
 * 类名：SkuGmEntity
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.dict.entity.sku;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

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
@TableName("djmall_dict_product_sku_gm")
public class SkuGmEntity implements Serializable {
    /**
     * skuGmID
     */
    @TableId(type = IdType.AUTO)
    @Mapping("skuGmId")
    private Integer id;
    /**
     * 商品类型
     */
    private String productType;
    /**
     * 商品属性ID
     */
    private Integer attrId;
}
