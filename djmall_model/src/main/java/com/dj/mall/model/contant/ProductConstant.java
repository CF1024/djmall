/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_model
 * 类名：ProductConstant
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.model.contant;
/**
 * @author chengf
 * @date 2020/6/3 17:56
 * 权限管理常量接口
 */
public interface ProductConstant {
    /**
     * session里的登录人的key
     */
    String SESSION_USER = "USER";
    /**
     * 商品未选中
     */
    Integer NOT_CHECKED = 1;
    /**
     * 最大购买量 200
     */
    Integer MAX_QUANTITY = 200;
    /**
     * 0
     */
    Integer ZERO = 0;
}
