/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：AttrValueVOReq
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.vo.dict.attr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chengf
 * @date 2020/6/27 20:40
 * 商品属性值
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AttrValueVOReq implements Serializable {
    /**
     * 商品属性值id
     */
    private Integer attrValueId;
    /**
     *商品属性ID
     */
    private Integer attrId;
    /**
     *商品属性值
     */
    private String attrValue;
    /**
     * 当前页
     */
    private Integer pageNo = 1;
    /**
     * 每页条数
     */
    private Integer pageSize = 5;
}
