/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_provider
 * 类名：UserBO
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.bo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chengf
 * @date 2020/6/3 14:19
 * 用户bo
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserBO implements Serializable {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String userPhone;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 用户激活状态
     */
    private String userStatus;

    /**
     * 性别
     */
    private String userSex;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 用户状态
     */
    private String isDel;
    /**
     * 非此表字段 角色名
     */
    private String roleName;

    /**
     * 非此表字段 用户角色关联表
     */
    private Integer userRole;
    /**
     * 非此表字段 用户性别
     */
    private String userSexShow;
    /**
     * 非此表字段 最后登录时间
     */
    private LocalDateTime lastLoginTime;
    /**
     * 非此表字段 激活状态
     */
    private String userStatusShow;
    /**
     * 非此表字段 用户角色集合
     */
    private List<Integer> userRoleList;
    /**
     * 非此表字段 用户性别集合
     */
    private List<String> userSexList;
}
