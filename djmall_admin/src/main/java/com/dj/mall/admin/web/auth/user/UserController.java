package com.dj.mall.admin.web.auth.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.resource.ResourceVOResp;
import com.dj.mall.admin.vo.auth.user.UserRoleVOReq;
import com.dj.mall.admin.vo.auth.user.UserVOReq;
import com.dj.mall.admin.vo.auth.user.UserVOResp;
import com.dj.mall.auth.api.user.UserApi;
import com.dj.mall.auth.api.user.UserRoleApi;
import com.dj.mall.auth.dto.resource.ResourceDTO;
import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.auth.dto.user.UserRoleDTO;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.contant.AuthConstant;
import com.dj.mall.model.contant.DictConstant;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chengf
 * @date 2020/6/3 15:13
 * 用户控制层
 */
@RestController
@RequestMapping("/auth/user/")
public class UserController {
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
     * 用户登录
     * @param userName
     * @param userPwd
     * @param session
     * @return
     * @throws Exception
     */
    @GetMapping("login")
    public ResultModel<Object> login(String userName, String userPwd, HttpSession session) throws Exception {
        //非空判断
        Assert.hasText(userName, "请输入账号");
        Assert.hasText(userPwd, "请输入密码");
        UserDTO USER = userApi.findUserByNameAndPwd(userName, userPwd);
        session.setAttribute(AuthConstant.SESSION_USER, USER);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, userPwd);
        subject.login(token);
        return new ResultModel<>().success("登录成功");
    }

    /**
     * 去重
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @GetMapping("deDuplicate")
    public Boolean deDuplicate(UserVOReq userVOReq) throws Exception {
        return userApi.deDuplicate(DozerUtil.map(userVOReq, UserDTO.class));
    }

    /**
     * 新增用户
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("addUser")
    public ResultModel<Object> addUser(UserVOReq userVOReq) throws Exception {
        //非空判断
        Assert.hasText(userVOReq.getUserName(), "请输入账号");
        Assert.hasText(userVOReq.getNickName(), "请输入昵称");
        Assert.hasText(userVOReq.getUserPhone(), "请输入手机号");
        Assert.hasText(userVOReq.getUserEmail(), "请输入邮箱");
        Assert.hasText(userVOReq.getUserPwd(), "请输入密码");
        userApi.addUser(DozerUtil.map(userVOReq, UserDTO.class));
        return new ResultModel<>().success("新增成功，如果你是商家请注意您所填写的邮箱信息，前往邮箱激活账户，如果不是商家请忽略哟😊");
    }

    /**
     * 用户展示
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("show")
    @RequiresPermissions(value = PermissionsCode.USER_MANAGE)
    public ResultModel<Object> show(UserVOReq userVOReq) throws Exception {
        PageResult pageResult = userApi.findAll(DozerUtil.map(userVOReq, UserDTO.class));
        pageResult.toBuilder().list(DozerUtil.mapList(pageResult.getList(), UserVOResp.class));
        return new ResultModel<>().success(pageResult);
    }

    /**
     * 修改用户
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @PutMapping("updateUser")
    @RequiresPermissions(value = PermissionsCode.USER_UPDATE_BTN)
    public ResultModel<Object> updateUser(UserVOReq userVOReq) throws Exception {
        //非空判断
        Assert.hasText(userVOReq.getUserName(), "请输入账号");
        Assert.hasText(userVOReq.getNickName(), "请输入昵称");
        Assert.hasText(userVOReq.getUserPhone(), "请输入手机号");
        Assert.hasText(userVOReq.getUserEmail(), "请输入邮箱");
        userApi.updateUser(DozerUtil.map(userVOReq, UserDTO.class));
        return new ResultModel<>().success("修改成功");
    }

    /**
     * 用户激活
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("activationUser")
    @RequiresPermissions(value = PermissionsCode.USER_ACTIVATION_BTN)
    public ResultModel<Object> activationUser(Integer id) throws Exception {
        userApi.updateUserStatus(id);
        return new ResultModel<>().success("激活成功");
    }

    /**
     * 重置密码
     * @param userVOReq
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping("resetPwd")
    @RequiresPermissions(value = PermissionsCode.USER_RESET_PASSWORD_BTN)
    public ResultModel<Object> resetPwd(UserVOReq userVOReq, HttpSession session) throws Exception {
        //当前登录人
        UserDTO USER = (UserDTO) session.getAttribute(AuthConstant.SESSION_USER);
        userVOReq.setSessionUser(USER.getNickName());
        userApi.resetPwd(DozerUtil.map(userVOReq, UserDTO.class));
        return new ResultModel<>().success("重置密码成功");
    }

    /**
     * 强制修改密码
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @PutMapping("forceUpdatePwd")
    public ResultModel<Object> forceUpdatePwd(UserVOReq userVOReq) throws Exception {
        Assert.hasText(userVOReq.getUserPwd(), "请输入密码");
        userApi.forceUpdatePwd(DozerUtil.map(userVOReq, UserDTO.class));
        return new ResultModel<>().success("修改密码成功，请使用新密码进行登录");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     * @throws Exception
     */
    @PostMapping("removeUser")
    @RequiresPermissions(value = PermissionsCode.USER_DELETE_BTN)
    public ResultModel<Object> removeUser(@RequestParam("ids[]") ArrayList<Integer> ids) throws Exception {
        userApi.removeUser(ids, DictConstant.HAVE_DEL);
        return new ResultModel<>().success("删除成功");
    }

    /**
     * 用户授权角色
     * @param userRoleVOReq
     * @return
     * @throws Exception
     */
    @PutMapping("auth")
    @RequiresPermissions(value = PermissionsCode.USER_AUTH_BTN)
    public ResultModel<Object> auth(UserRoleVOReq userRoleVOReq) throws Exception {
        userRoleApi.updateUserRole(DozerUtil.map(userRoleVOReq, UserRoleDTO.class));
        return new ResultModel<>().success("授权成功");
    }

    /**
     * 获取验证码
     * @param userPhone
     * @return
     * @throws Exception
     */
    @PostMapping("sendCode")
    public ResultModel<Object> sendCode(String userPhone) throws Exception {
        userApi.sendCode(userPhone);
        return new ResultModel<>().success("发送成功，请注意查收信息");
    }

    /**
     * 手机号登录
     * @param userPhone 手机号
     * @param verifyCode 验证码
     * @return
     * @throws Exception
     */
    @GetMapping("phoneLogin")
    public ResultModel<Object> phoneLogin(String userPhone, String verifyCode, HttpSession session) throws Exception {
        //非空判断
        Assert.hasText(userPhone, "请输入手机号");
        Assert.hasText(verifyCode, "请输入验证码");
        UserDTO USER = userApi.findUserByPhoneAndCode(userPhone, verifyCode);
        session.setAttribute(AuthConstant.SESSION_USER, USER);
        return new ResultModel<>().success("登录成功");
    }

    /**
     * 忘记密码
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("forgetPwd")
    public ResultModel<Object> forgetPwd(UserVOReq userVOReq) throws Exception {
        //非空判断
        Assert.hasText(userVOReq.getUserPhone(), "请输入手机号");
        Assert.hasText(userVOReq.getVerifyCode(), "请输入验证码");
        Assert.hasText(userVOReq.getUserPwd(), "请输入密码");
        userApi.updatePwdByPhone(DozerUtil.map(userVOReq, UserDTO.class));
        return new ResultModel<>().success("修改成功，请使用新密码进行登录");
    }

    /**
     * 展示左侧菜单
     * @param session
     * @return
     * @throws Exception
     */
    @GetMapping("showMenu")
    public ResultModel<Object> showMenu(HttpSession session) throws Exception {
        //从session中取出用户已关联资源
         UserDTO USER = (UserDTO) session.getAttribute(AuthConstant.SESSION_USER);
        //区分按钮和菜单
        List<ResourceDTO> resourceList = USER.getPermissionList().stream().filter(resource -> DictConstant.MENU.equals(resource.getResourceType())).collect(Collectors.toList());
        return new ResultModel<>().success(DozerUtil.mapList(resourceList, ResourceVOResp.class));
    }
}
