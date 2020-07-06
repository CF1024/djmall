/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：ResourceVOReq
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.vo.auth.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chengf
 * @date 2020/6/3 22:50
 * 资源VOReq 接收实体
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ResourceVOReq implements Serializable {
    /**
     * 资源id
     */
    private Integer resourceId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     *资源路径
     */
    private String url;

    /**
     * 资源父级
     */
    private Integer parentId;

    /**
     * 资源状态 0 未删除 1 已删除
     */
    private String isDel;

    /**
     * 资源类型
     */
    private String resourceType;
    /**
     * 资源编码
     */
    private String resourceCode;
}
