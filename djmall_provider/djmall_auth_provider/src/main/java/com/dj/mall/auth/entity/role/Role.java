/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_provider
 * 类名：Role
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.entity.role;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import java.io.Serializable;

/**
 * @author chengf
 * @date 2020/6/3 13:53
 * 角色实体类
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("djmall_auth_role")
public class Role implements Serializable {

    /**
     * 角色id
     */
    @TableId(type = IdType.AUTO)
    @Mapping("roleId")
    private Integer id;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色状态 0 未删除 1 已删除
     */
    private String isDel;
}
