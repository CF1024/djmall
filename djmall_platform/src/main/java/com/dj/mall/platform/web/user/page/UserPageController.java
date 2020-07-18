/*
 * 作者：CF
 * 日期：2020-07-11 16:09
 * 项目：djmall
 * 模块：djmall_platform
 * 类名：UserController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.platform.web.user.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.auth.api.cart.ShoppingCartApi;
import com.dj.mall.auth.api.user.UserApi;
import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.cmpt.RedisApi;
import com.dj.mall.dict.api.dict.BaseDataApi;
import com.dj.mall.model.contant.DictConstant;
import com.dj.mall.model.contant.RedisConstant;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.platform.vo.auth.cart.ShoppingCartVOResp;
import com.dj.mall.platform.vo.dict.dict.BaseDataVOResp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class UserPageController {
    /**
     * 用户api
     */
    @Reference
    private UserApi userApi;
    /**
     * 字典数据
     */
    @Reference
    private BaseDataApi baseDataApi;
    /**
     * redisApi
     */
    @Reference
    private RedisApi redisApi;
    /**
     * 购物车api
     */
    @Reference
    private ShoppingCartApi shoppingCartApi;

    /**
     * 去注册用户
     * @param model ModelMap
     * @return 注册用户前台
     * @throws Exception 异常
     */
    @GetMapping("toRegister")
    public String toRegister(ModelMap model) throws Exception {
        model.put("sexList", DozerUtil.mapList(baseDataApi.findBaseDataByParentCode(DictConstant.USER_SEX), BaseDataVOResp.class));
        return "user/register";
    }

    /**
     * 去登录页
     * @return 登录前台页面
     */
    @GetMapping("toLogin")
    public String toLogin() {
        return "user/login";
    }

    /**
     * 去我的购物车
     * @param TOKEN 令牌密钥 用户唯一标识
     * @param model ModelMap
     * @return 购物车页面
     * @throws Exception 异常
     */
    @GetMapping("cart/toMyShoppingCart")
    public String toMyShoppingCart(String TOKEN, ModelMap model) throws Exception {
        model.put("cartList", DozerUtil.mapList(shoppingCartApi.findCartByToken(TOKEN), ShoppingCartVOResp.class));
        return "cart/show";
    }

}
