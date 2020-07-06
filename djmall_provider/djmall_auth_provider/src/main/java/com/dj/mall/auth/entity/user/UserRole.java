/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_provider
 * 类名：UserRole
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chengf
 * @date 2020/6/3 13:53
 * 用户角色实体类
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("djmall_auth_user_role")
public class UserRole implements Serializable {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 角色id
     */
    private Integer roleId;
}
