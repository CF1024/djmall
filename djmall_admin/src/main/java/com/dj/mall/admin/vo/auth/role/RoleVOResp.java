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
 * 角色VOResp 返回实体
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RoleVOResp implements Serializable {
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
}
