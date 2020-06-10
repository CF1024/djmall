package com.dj.mall.auth.dto.user;

import com.dj.mall.auth.dto.resource.ResourceDTO;
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
 * 用户dto
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
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

    /**
     * 当前页
     */
    private Integer pageNo = 1;
    /**
     * 每页条数
     */
    private Integer pageSize = 5;

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
    /**
     * 当前登录人
     */
    private String sessionUser;
    /**
     * 非此表字段 用户权限集合
     */
    private List<ResourceDTO> permissionList;

}
