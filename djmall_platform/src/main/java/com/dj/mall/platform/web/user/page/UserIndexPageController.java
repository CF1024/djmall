/*
 * 作者：CF
 * 日期：2020-07-19 13:24
 * 项目：djmall
 * 模块：djmall_platform
 * 类名：UserIndexPageController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.platform.web.user.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.auth.api.user.UserApi;
import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.cmpt.RedisApi;
import com.dj.mall.model.contant.RedisConstant;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.platform.vo.auth.user.UserVOResp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/index/")
public class UserIndexPageController {
    /**
     * redisApi
     */
    @Reference
    private RedisApi redisApi;
    /**
     * userApi
     */
    @Reference
    private UserApi userApi;

    /**
     * 个人中心 index
     * @param TOKEN 令牌密钥 用户唯一标识
     * @param model ModelMap
     * @return  index页面
     */
    @GetMapping("toIndex")
    public String toIndex(String TOKEN, ModelMap model) throws Exception {
        UserDTO userDTO = redisApi.get(RedisConstant.USER_TOKEN+TOKEN);
        model.put("TOKEN", TOKEN);
        model.put("user", userDTO);
        return "user/index/index";
    }

    /**
     * top
     * @return top
     */
    @GetMapping("toTop")
    public String toTop() {
        return "user/index/top";
    }

    /**
     * 左
     * @return left
     */
    @GetMapping("toLeft")
    public String toLeft() {
        return "user/index/left";
    }

    /**
     * right
     * @return right
     */
    @GetMapping("toRight")
    public String toRight() {
        return "user/index/right";
    }
}
