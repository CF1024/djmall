/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_dict_provider
 * 类名：AttrEntity
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.dict.entity.attr;

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
 * @date 2020/6/27 20:40
 * 商品属性
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("djmall_dict_product_attr")
public class AttrEntity implements Serializable {
    /**
     * 商品属性id
     */
    @TableId(type = IdType.AUTO)
    @Mapping("attrId")
    private Integer id;
    /**
     *商品属性名
     */
    private String attrName;
}
