package com.dj.mall.admin.web.auth.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.user.UserVOReq;
import com.dj.mall.admin.vo.auth.user.UserVOResp;
import com.dj.mall.auth.api.user.UserApi;
import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.contant.AuthConstant;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author chengf
 * @date 2020/6/3 15:13
 * 用户控制层
 */
@RestController
@RequestMapping("/auth/user/")
public class UserController {
    @Reference
    private UserApi userApi;

    @GetMapping("login")
    public ResultModel<Object> login(String userName, String userPwd, HttpSession session) throws Exception {
        //非空判断
        Assert.hasText(userName, "请输入账号");
        Assert.hasText(userPwd, "请输入密码");
        UserDTO USER = userApi.findUserByNameAndPwd(userName, userPwd);
        session.setAttribute(AuthConstant.SESSION_USER, USER);
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
    public ResultModel<Object> show(UserVOReq userVOReq) throws Exception {
        PageResult pageResult = userApi.findAll(DozerUtil.map(userVOReq, UserDTO.class));
        pageResult.toBuilder().list(DozerUtil.mapList(pageResult.getList(), UserVOResp.class));
        return new ResultModel<>().success(pageResult);
    }
}
