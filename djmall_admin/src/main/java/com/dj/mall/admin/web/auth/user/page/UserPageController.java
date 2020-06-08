package com.dj.mall.admin.web.auth.user.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.role.RoleVOReq;
import com.dj.mall.admin.vo.auth.role.RoleVOResp;
import com.dj.mall.admin.vo.auth.user.UserVOResp;
import com.dj.mall.auth.api.role.RoleApi;
import com.dj.mall.auth.api.user.UserApi;
import com.dj.mall.auth.dto.role.RoleDTO;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.contant.AuthConstant;
import com.dj.mall.model.util.DozerUtil;
import org.omg.PortableInterceptor.INACTIVE;
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
    @Reference
    private RoleApi roleApi;
    @Reference
    private UserApi userApi;
    /**
     * 去登录
     * @return
     */
    @RequestMapping("toLogin")
    public String toLogin() {
        return "auth/user/login";
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @RequestMapping("logout")
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
    public String toShow(ModelMap model) throws Exception {
        //查询角色
        model.put("roleList", DozerUtil.mapList(roleApi.findAll(DozerUtil.map(RoleVOReq.class, RoleDTO.class)).getList(), RoleVOResp.class));
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
    public String toUpdateUser(@PathVariable("userId") Integer userId, ModelMap model) throws Exception {
        model.put("user", DozerUtil.map( userApi.findUserById(userId), UserVOResp.class));
        return "auth/user/update";
    }

}
