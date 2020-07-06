/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_api
 * 类名：RoleResourceDTO
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.dto.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chengf
 * @date 2020/6/3 22:51
 * 角色资源DTOReq 接收实体
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RoleResourceDTO implements Serializable {
    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 资源id
     */
    private Integer resourceId;
}
