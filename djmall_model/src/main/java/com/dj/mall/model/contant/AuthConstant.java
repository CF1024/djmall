package com.dj.mall.model.contant;
/**
 * @author chengf
 * @date 2020/6/3 17:56
 * 权限管理常量接口
 */
public interface AuthConstant {
    /**
     * session里的登录人的key
     */
    String SESSION_USER = "USER";
    /**
     * 强制修改密码 -100
     */
    Integer FORCE_UPDATE_PWD_INT = -100;
    /**
     * 放过的资源路径 --
     */
    String URL = "--";
    /**
     * 商家
     */
    Integer BUSINESS = 2;
    /**
     * 验证邮箱激活
     */
    String ACTIVATE = "验证邮箱激活";
    /**
     * 密码重置
     */
    String RESET_PWD = "密码重置";
    /**
     * 6位随机数
     */
    Integer RANDOM_SIX = 6;
    /**
     * 验证码失效时间 5分钟
     */
    Integer FAILURE_TIME = 5;
    /**
     * 忘记密码
     */
    String FORGET_PWD = "忘记密码";

    /**
     * 用户已关联资源session
     */
    String USER_RESOURCE = "USER_RESOURCE";
}
