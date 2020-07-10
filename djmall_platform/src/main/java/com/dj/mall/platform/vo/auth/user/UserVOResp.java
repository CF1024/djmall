/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：UserVOResp
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.platform.vo.auth.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chengf
 * @date 2020/6/3 14:39
 * 用户视图对象resp
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserVOResp implements Serializable {
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
     * 密码
     */
    private String userPwd;

    /**
     * 盐
     */
    private String salt;

    /**
     * 手机号
     */
    private String userPhone;

    /**
     * 手机验证码
     */
    private String verifyCode;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 用户激活状态 5：已激活 6：未激活
     */
    private String userStatus;

    /**
     * 性别  8:男 9:女  10:保密
     */
    private String userSex;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 验证码失效时间
     */
    private LocalDateTime invalidateTime;
    /**
     * 用户状态 2：未删除 3 已删除
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


}
