/*
 * 作者：CF
 * 日期：2020-07-11 16:09
 * 项目：djmall
 * 模块：djmall_platform
 * 类名：UserController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.platform.web.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.auth.api.user.UserApi;
import com.dj.mall.auth.dto.cart.ShoppingCartDTO;
import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.auth.dto.user.UserTokenDTO;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.platform.vo.auth.cart.ShoppingCartVOReq;
import com.dj.mall.platform.vo.auth.user.UserVOReq;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/")
public class UserController {
    /**
     * 用户api
     */
    @Reference
    private UserApi userApi;

    /**
     * 注册去重
     * @param userVOReq userVOReq
     * @return Boolean
     * @throws Exception 异常
     */
    @GetMapping
    public Boolean deDuplicate(UserVOReq userVOReq) throws Exception {
        return userApi.deDuplicate(DozerUtil.map(userVOReq, UserDTO.class));
    }

    /**
     * 用户注册
     * @param userVOReq userVOReq
     * @return 注册成功
     * @throws Exception 异常
     */
    @PostMapping
    public ResultModel<Object> register(UserVOReq userVOReq) throws Exception {
        //非空判断
        Assert.hasText(userVOReq.getUserName(), "请输入账号");
        Assert.hasText(userVOReq.getUserPhone(), "请输入手机号");
        Assert.hasText(userVOReq.getUserEmail(), "请输入邮箱");
        Assert.hasText(userVOReq.getUserPwd(), "请输入密码");
        userApi.addUser(DozerUtil.map(userVOReq, UserDTO.class));
        return new ResultModel<>().success("注册成功");
    }

    /**
     * 普通用户登录
     * @param userName 用户名
     * @param userPwd 密码
     * @return userTokenDTO
     * @throws Exception 异常信息
     */
    @GetMapping("login")
    public ResultModel<Object> login(String userName, String userPwd) throws Exception {
        Assert.hasText(userName, "用户名不能为空");
        Assert.hasText(userPwd, "密码不能为空");
        UserTokenDTO userTokenDTO = userApi.findUserTokenByNameAndPwd(userName, userPwd);
        return new ResultModel<>().success(userTokenDTO);
    }

    /**
     * 退出登录
     * @param token token
     * @return 退出成功
     * @throws Exception 异常信息
     */
    @PostMapping("toLogout")
    public ResultModel<Object> toLogout(String token) throws Exception {
        userApi.toLogout(token);
        return new ResultModel<>().success("退出成功");
    }

    /**
     * 添购物车
     * @param shoppingCartVOReq shoppingCartVOReq
     * @param TOKEN 令牌密钥 用户唯一标识
     * @return 添加购物车成功
     * @throws Exception 异常
     */
    @PostMapping("cart/addToShoppingCart")
    public ResultModel<Object> addToShoppingCart(ShoppingCartVOReq shoppingCartVOReq, String TOKEN) throws Exception {
        userApi.addToShoppingCart(DozerUtil.map(shoppingCartVOReq, ShoppingCartDTO.class), TOKEN);
        return new ResultModel<>().success("添加购物车成功");
    }
}
