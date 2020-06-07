package com.dj.mall.admin.vo.auth.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chengf
 * @date 2020/6/3 22:49
 * 角色资源VOReq 接收实体
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RoleResourceVOReq implements Serializable {
    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 资源id
     */
    private Integer resourceId;
}
