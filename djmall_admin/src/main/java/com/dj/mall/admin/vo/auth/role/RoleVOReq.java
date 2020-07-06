/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：RoleVOReq
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.vo.auth.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author chengf
 * @date 2020/6/3 22:49
 * 角色VOReq 接收实体
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RoleVOReq implements Serializable {
    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色状态 0 未删除 1 已删除
     */
    private String isDel;
    /**
     * 当前页
     */
    private Integer pageNo = 1;
    /**
     * 每页条数
     */
    private Integer pageSize = 10;
    /**
     * 资源id集合
     */
    private List<Integer> resourceIdList;

}
