/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：AttrVOResp
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.platform.vo.dict.attr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class AttrVOResp implements Serializable {
    /**
     * 商品属性id
     */
    private Integer attrId;
    /**
     *商品属性名
     */
    private String attrName;
    /**
     *商品属性值
     */
    private String attrValue;
    /**
     *商品属性值id
     */
    private String attrValueIds;
    /**
     *商品属性值
     */
    private String attrValues;
}
