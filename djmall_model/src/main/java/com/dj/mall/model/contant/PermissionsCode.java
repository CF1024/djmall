package com.dj.mall.model.contant;
/**
 * @author chengf
 * @date 2020/6/12 17:20
 * 权限编码
 */
public interface PermissionsCode {
    /**
     * 权限管理 菜单
     */
    String PERMISSION_MANAGE = "PERMISSION_MANAGE";
    //==============================用户==================================//
    /**
     * 用户管理 菜单
     */
    String USER_MANAGE = "USER_MANAGE";
    /**
     * 用户修改 按钮
     */
    String USER_UPDATE_BTN = "USER_UPDATE_BTN";
    /**
     * 用户激活 按钮
     */
    String USER_ACTIVATION_BTN = "USER_ACTIVATION_BTN";
    /**
     * 用户重置密码 按钮
     */
    String USER_RESET_PASSWORD_BTN = "USER_RESET_PASSWORD_BTN";
    /**
     * 用户删除 按钮
     */
    String USER_DELETE_BTN = "USER_DELETE_BTN";
    /**
     * 用户授权 按钮
     */
    String USER_AUTH_BTN = "USER_AUTH_BTN";

    //==============================角色==================================//
    /**
     * 角色管理 菜单
     */
    String ROLE_MANAGE = "ROLE_MANAGE";
    /**
     * 角色新增 按钮
     */
    String ROLE_ADD_BTN = "ROLE_ADD_BTN";
    /**
     * 角色修改 按钮
     */
    String ROLE_UPDATE_BTN = "ROLE_UPDATE_BTN";

    /**
     * 角色关联资源 按钮
     */
    String ROLE_RELATED_RESOURCE_BTN = "ROLE_RELATED_RESOURCE_BTN";
    /**
     * 角色删除 按钮
     */
    String ROLE_DELETE_BTN = "ROLE_DELETE_BTN";

    //==============================资源==================================//
    /**
     * 资源管理 菜单
     */
    String RESOURCE_MANAGE = "RESOURCE_MANAGE";
    /**
     * 资源新增 按钮
     */
    String RESOURCE_ADD_BTN = "RESOURCE_ADD_BTN";
    /**
     * 资源修改 按钮
     */
    String RESOURCE_UPDATE_BTN = "RESOURCE_UPDATE_BTN";
    /**
     * 资源删除 按钮
     */
    String RESOURCE_DELETE_BTN = "RESOURCE_DELETE_BTN";

    //==============================字典==================================//
    /**
     * 字典管理 菜单
     */
    String DICT_MANAGE = "DICT_MANAGE";
    /**
     * 字典新增 按钮
     */
    String DICT_ADD_BTN = "DICT_ADD_BTN";
    /**
     * 字典修改 按钮
     */
    String DICT_UPDATE_BTN = "DICT_UPDATE_BTN";

    //==============================运费==================================//

    /**
     * 运费管理 菜单
     */
    String FREIGHT_MANAGE = "FREIGHT_MANAGE";
    /**
     * 运费新增 按钮
     */
    String FREIGHT_ADD_BTN = "FREIGHT_ADD_BTN";
    /**
     * 运费修改 按钮
     */
    String FREIGHT_UPDATE_BTN = "FREIGHT_UPDATE_BTN";

    //==============================商品属性==================================//

    /**
     * 属性管理 菜单
     */
    String ATTR_MANAGE = "ATTR_MANAGE";
    /**
     * 属性_新增 按钮
     */
    String ATTR_ADD_BTN = "ATTR_ADD_BTN";
    /**
     * 关联属性值 按钮
     */
    String RELATED_ATTR_VALUE_BTN = "RELATED_ATTR_VALUE_BTN";
    /**
     * 属性值_新增 按钮
     */
    String ATTR_VALUE_ADD_BTN = "ATTR_VALUE_ADD_BTN";
    /**
     * 属性值_删除 按钮
     */
    String ATTR_VALUE_DELETE_BTN = "ATTR_VALUE_DELETE_BTN";

    //==============================sku==================================//

    /**
     * 通用SKU维护 菜单
     */
    String SKU_GM_MANAGE = "SKU_GM_MANAGE";
    /**
     * 通用SKU维护_新增 按钮
     */
    String SKU_GM_ADD_BTN = "SKU_GM_ADD_BTN";
    /**
     * 关联属性 按钮
     */
    String RELATED_ATTR_BTN = "RELATED_ATTR_BTN";

    //==============================商品==================================//

}
