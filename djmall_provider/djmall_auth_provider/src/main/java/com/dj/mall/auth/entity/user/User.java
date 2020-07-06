/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_provider
 * 类名：User
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.dozer.Mapping;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chengf
 * @date 2020/6/3 13:52
 * 用户实体
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("djmall_auth_user")
public class User implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    @Mapping("userId")
    private Integer id;

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
}
