/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_api
 * 类名：TreeData
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.dto.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * @author chengf
 * @date 2020/6/5 13:36
 * 关联资源zTree
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TreeData implements Serializable {
    /**
     * 资源id
     */
    private Integer id;
    /**
     * 名字
     */
    private String name;
    /**
     * 父级ID
     */
    private Integer parentId;
    /**
     * 是否打开
     */
    private boolean open = true;
    /**
     * 默认勾选
     */
    private boolean checked = false;
}
