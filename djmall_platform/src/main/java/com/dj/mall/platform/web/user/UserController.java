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
import com.dj.mall.auth.api.cart.ShoppingCartApi;
import com.dj.mall.auth.api.user.UserApi;
import com.dj.mall.auth.dto.address.UserAddressDTO;
import com.dj.mall.auth.dto.cart.ShoppingCartDTO;
import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.auth.dto.user.UserTokenDTO;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.platform.vo.auth.address.AreaVoResp;
import com.dj.mall.platform.vo.auth.address.UserAddressVoReq;
import com.dj.mall.platform.vo.auth.address.UserAddressVoResp;
import com.dj.mall.platform.vo.auth.cart.ShoppingCartVOReq;
import com.dj.mall.platform.vo.auth.cart.ShoppingCartVOResp;
import com.dj.mall.platform.vo.auth.user.UserVOReq;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/user/")
public class UserController {
    /**
     * 用户api
     */
    @Reference
    private UserApi userApi;
    /**
     * 购物车api
     */
    @Reference
    private ShoppingCartApi shoppingCartApi;

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

    @PostMapping("sendCode")
    public ResultModel<Object> sendCode(String userPhone) throws Exception {
        Assert.hasText(userPhone, "手机号不能为空");
        userApi.sendCode(userPhone);
        return new ResultModel<>().success();
    }
    /**
     * 退出登录
     * @param TOKEN token
     * @return 退出成功
     * @throws Exception 异常信息
     */
    @DeleteMapping("toLogout")
    public ResultModel<Object> toLogout(String TOKEN) throws Exception {
        userApi.deleteToken(TOKEN);
        return new ResultModel<>().success("退出成功");
    }

    /**
     * 修改用户信息
     * @param userVOReq  userVOReq
     * @param file 头像文件
     * @return userTokenDTO
     * @throws Exception 异常信息
     */
    @PutMapping("updateGeneralUser")
    public ResultModel<Object> updateGeneralUser(UserVOReq userVOReq, MultipartFile file) throws Exception {
        //判断file文件流是不是空
        byte[] bytes = null;
        if (!StringUtils.isEmpty(file.getOriginalFilename())) {
            String fileName = UUID.randomUUID().toString().replace("-", "") + Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
            userVOReq.setUserImg(fileName);
            bytes = file.getBytes();
        }
        UserTokenDTO userTokenDTO = userApi.updateGeneralUser(DozerUtil.map(userVOReq, UserDTO.class), bytes);
        return new ResultModel<>().success(userTokenDTO);
    }



/*===========================================================收货地址==============================================================*/

    /**
     * 展示收货地址
     * @param TOKEN 令牌密钥 用户唯一标识
     * @return  UserAddressVoResp
     * @throws Exception 异常信息
     */
    @GetMapping("address/show")
    public ResultModel<Object> show(@RequestHeader("TOKEN") String TOKEN) throws Exception {
        return new ResultModel<>().success(DozerUtil.mapList(userApi.findAddressAll(TOKEN), UserAddressVoResp.class));
    }

    /**
     * 三级联动 根据父级id查数据
     * @param parentId 父级id
     * @return AreaVoResp
     * @throws Exception  异常信息
     */
    @GetMapping("address/getAreaByParentId")
    public ResultModel<Object> getAreaByParentId(Integer parentId) throws Exception {
        return new ResultModel<>().success(DozerUtil.mapList(userApi.getAreaByParentId(parentId), AreaVoResp.class));
    }

    /**
     * 新增收货地址
     * @param userAddressVoReq userAddressVoReq
     * @param TOKEN 令牌密钥 用户唯一标识
     * @return  ResultModel
     * @throws Exception 异常信息
     */
    @PostMapping("address/add")
    public ResultModel<Object> add(UserAddressVoReq userAddressVoReq, String TOKEN) throws Exception {
        userApi.newShippingAddress(DozerUtil.map(userAddressVoReq, UserAddressDTO.class), TOKEN);
        return new ResultModel<>().success();
    }
    /**
     * 修改收货地址
     * @param userAddressVoReq userAddressVoReq
     * @return  ResultModel
     * @throws Exception 异常信息
     */
    @PutMapping("address/update")
    public ResultModel<Object> update(UserAddressVoReq userAddressVoReq) throws Exception {
        userApi.updateAddressById(DozerUtil.map(userAddressVoReq, UserAddressDTO.class));
        return new ResultModel<>().success();
    }

    /**
     * 删除收货地址
     * @param id 地址id
     * @return  ResultModel
     * @throws Exception 异常信息
     */
    @PutMapping("address/remove")
    public ResultModel<Object> remove(Integer id) throws Exception {
        userApi.removeAddressById(id);
        return new ResultModel<>().success();
    }


/*===========================================================我的购物车==============================================================*/
    /**
     * 添购物车
     * @param shoppingCartVOReq shoppingCartVOReq
     * @param TOKEN 令牌密钥 用户唯一标识
     * @return 添加购物车成功
     * @throws Exception 异常
     */
    @PostMapping("cart/addToShoppingCart")
    public ResultModel<Object> addToShoppingCart(ShoppingCartVOReq shoppingCartVOReq, String TOKEN, Integer buyNow) throws Exception {
        Integer cartId = userApi.addToShoppingCart(DozerUtil.map(shoppingCartVOReq, ShoppingCartDTO.class), TOKEN, buyNow);
        return new ResultModel<>().success(cartId);
    }

    /**
     * 金额计算
     * @param ids 购物车id集合
     * @return 购物车VOResp
     * @throws Exception 异常信息
     */
    @PostMapping("cart/getTotal")
    public ResultModel<Object> getTotal(@RequestParam("ids[]") ArrayList<Integer> ids, String TOKEN) throws Exception {
        return new ResultModel<>().success(DozerUtil.map(shoppingCartApi.getTotal(ids, TOKEN), ShoppingCartVOResp.class));
    }

    /**
     * 根据id修改购物车
     * @param shoppingCartVOReq 购物车dto
     * @return ResultModel
     * @throws Exception 异常
     */
    @PutMapping("cart/updateCart")
    public ResultModel<Object> updateCart(ShoppingCartVOReq shoppingCartVOReq) throws Exception {
        shoppingCartApi.updateCart(DozerUtil.map(shoppingCartVOReq, ShoppingCartDTO.class));
        return new ResultModel<>().success();
    }

    /**
     *根据购物车id删除购物车
     * @param ids  购物车id集合
     * @return 删除成功
     * @throws Exception 异常
     */
    @DeleteMapping("cart/deleteCart")
    public ResultModel<Object> deleteCart(Integer[] ids) throws Exception {
        shoppingCartApi.deleteCartById(ids);
        return new ResultModel<>().success("删除成功");
    }
}
