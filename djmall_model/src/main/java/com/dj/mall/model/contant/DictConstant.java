/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_model
 * 类名：DictConstant
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.model.contant;
/**
 * @author chengf
 * @date 2020/6/3 17:56
 * 字典数据常量接口
 */
public interface DictConstant {
    //==============================字典==================================//
    /**
     * 字典数据 system
     */
    String SYSTEM = "SYSTEM";
    /**
     * 状态：未删除
     */
    String NOT_DEL = "NOT_DEL";

    /**
     * 状态：已删除
     */
    String HAVE_DEL = "HAVE_DEL";

    /**
     * 用户状态：强制修改密码
     */
    String FORCE_UPDATE_PWD = "FORCE_UPDATE_PWD";

    /**
     * 用户激活状态
     */
    String ACTIVATE_STATUS = "ACTIVATE_STATUS";
    /**
     * 激活状态：已激活
     */
    String HAVE_ACTIVATE = "HAVE_ACTIVATE";

    /**
     * 激活状态：未激活
     */
    String NOT_ACTIVATE = "NOT_ACTIVATE";
    /**
     * 用户性别
     */
    String USER_SEX = "USER_SEX";
    /**
     * 资源类型
     */
    String RESOURCE_TYPE = "RESOURCE_TYPE";
    /**
     * 菜单
     */
    String MENU = "MENU";

    /**
     * 物流公司
     */
    String LOGISTICS_COMPANY = "LOGISTICS_COMPANY";

    /**
     * 商品属性
     */
    String PRODUCT_TYPE = "PRODUCT_TYPE";
    /**
     * 商品状态 上架
     */
    String PRODUCT_STATUS_UP = "PRODUCT_STATUS_UP";
    /**
     * 商品状态 下架
     */
    String PRODUCT_STATUS_DOWN = "PRODUCT_STATUS_DOWN";
    /**
     * 是否默认 默认
     */
    String HAVE_DEFAULT = "HAVE_DEFAULT";

    /**
     * 是否默认 不默认
     */
    String NOT_DEFAULT = "NOT_DEFAULT";


    //==============================运费==================================//

}
