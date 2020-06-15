package com.dj.mall.admin.web.auth.user.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.role.RoleVOReq;
import com.dj.mall.admin.vo.auth.role.RoleVOResp;
import com.dj.mall.admin.vo.auth.user.UserRoleVOResp;
import com.dj.mall.admin.vo.auth.user.UserVOResp;
import com.dj.mall.admin.vo.dict.dict.BaseDataVOResp;
import com.dj.mall.auth.api.role.RoleApi;
import com.dj.mall.auth.api.user.UserApi;
import com.dj.mall.auth.api.user.UserRoleApi;
import com.dj.mall.auth.dto.role.RoleDTO;
import com.dj.mall.dict.api.dict.BaseDataApi;
import com.dj.mall.model.contant.AuthConstant;
import com.dj.mall.model.contant.DictConstant;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author chengf
 * @date 2020/6/3 15:13
 * 用户控制层
 */
@Controller
@RequestMapping("/auth/user/")
public class UserPageController {
    /**
     * 角色api
     */
    @Reference
    private RoleApi roleApi;
    /**
     * 用户api
     */
    @Reference
    private UserApi userApi;
    /**
     * 用户角色api
     */
    @Reference
    private UserRoleApi userRoleApi;
    /**
     * 字典数据
     */
    @Reference
    private BaseDataApi baseDataApi;

    /**
     * 去登录
     * @return
     */
    @GetMapping("toLogin")
    public String toLogin() {
        return "auth/user/login";
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute(AuthConstant.SESSION_USER);
        return "auth/user/login";
    }

    /**
     * 去注册用户
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("toAddUser")
    public String toAddUser(ModelMap model) throws Exception {
        //查询角色
        model.put("roleList", DozerUtil.mapList(roleApi.findAll(DozerUtil.map(RoleVOReq.class, RoleDTO.class)).getList(), RoleVOResp.class));
        model.put("sexList", DozerUtil.mapList(baseDataApi.findBaseDataByParentCode(DictConstant.USER_SEX), BaseDataVOResp.class));
        return "auth/user/add";
    }

    /**
     * 邮箱激活 修改激活状态：已激活
     * @param id
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("toValidate/{id}")
    public String toValidate(@PathVariable("id") Integer id, ModelMap model) throws Exception {
        userApi.updateUserStatus(id);
        model.put("msg", "激活成功");
        return "auth/user/login";
    }

    /**
     * 去展示用户页面
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("toShow")
    @RequiresPermissions(value = PermissionsCode.USER_MANAGE)
    public String toShow(ModelMap model) throws Exception {
        //查询角色
        model.put("roleList", DozerUtil.mapList(roleApi.findAll(DozerUtil.map(RoleVOReq.class, RoleDTO.class)).getList(), RoleVOResp.class));
        model.put("sexList", DozerUtil.mapList(baseDataApi.findBaseDataByParentCode(DictConstant.USER_SEX), BaseDataVOResp.class));
        model.put("statusList", DozerUtil.mapList(baseDataApi.findBaseDataByParentCode(DictConstant.ACTIVATE_STATUS), BaseDataVOResp.class));
        return "auth/user/show";
    }

    /**
     * 去修改 根据id查
     * @param userId
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("toUpdateUser/{userId}")
    @RequiresPermissions(value = PermissionsCode.USER_UPDATE_BTN)
    public String toUpdateUser(@PathVariable("userId") Integer userId, ModelMap model) throws Exception {
        model.put("user", DozerUtil.map( userApi.findUserById(userId), UserVOResp.class));
        model.put("sexList", DozerUtil.mapList(baseDataApi.findBaseDataByParentCode(DictConstant.USER_SEX), BaseDataVOResp.class));
        return "auth/user/update";
    }

    /**
     * 去强制修改页面 根据账号查
     * @param userName
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("toForceUpdatePwd")
    public String toForceUpdatePwd(String userName, ModelMap model) throws Exception {
        model.put("user", DozerUtil.map( userApi.findUserByName(userName), UserVOResp.class));
        return "auth/user/force_update_pwd";
    }

    /**
     * 去授权
     * @param userId
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("toAuthUserRole/{userId}")
    @RequiresPermissions(value = PermissionsCode.USER_AUTH_BTN)
    public String toAuthUserRole(@PathVariable("userId") Integer userId, ModelMap model) throws Exception {
        //用户角色数据
        model.put("userRole", DozerUtil.map(userRoleApi.findUserRoleById(userId), UserRoleVOResp.class));
        //查询角色
        model.put("roleList", DozerUtil.mapList(roleApi.findAll(DozerUtil.map(RoleVOReq.class, RoleDTO.class)).getList(), RoleVOResp.class));
        return "auth/user/auth";
    }

    /**
     * 去手机号登录
     * @return
     */
    @GetMapping("toPhoneLogin")
    public String toPhoneLogin() {
        return "auth/user/phone_login";
    }

    /**
     * 去忘记密码
     * @return
     */
    @GetMapping("toForgetPwd")
    public String toForgetPwd() {
        return "auth/user/forget_pwd";
    }
}
