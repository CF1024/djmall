/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_model
 * 类名：AuthConstant
 * 版权所有(C), 2020. 所有权利保留
 */

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
     * 普通用户
     */
    Integer GENERAL_USER = 3;
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
    /**
     * 长度为0
     */
    Integer ZERO = 0;
    /**
     * 购物车选中状态为：1（未选中）
     */
    Integer CART_CHECKED_ONE = 1;
}
