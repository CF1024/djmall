/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_model
 * 类名：PageResult
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.model.base;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author chengf
 * @date 2020/4/7 19:46
 * 分页专用
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PageResult implements Serializable {

    /**
     * 总页数
     */
    private Long pages;

    /**
     * 数据
     */
    private List<?> list;


}
