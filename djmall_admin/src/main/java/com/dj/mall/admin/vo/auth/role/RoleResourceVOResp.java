/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：RoleResourceVOResp
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.vo.auth.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chengf
 * @date 2020/6/3 22:49
 * 角色资源VOResp 返回实体
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RoleResourceVOResp implements Serializable {
    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 资源id
     */
    private Integer resourceId;
}
