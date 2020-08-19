/*
 * 作者：CF
 * 日期：2020-08-19 16:03
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：StatementController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.web.statement;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.auth.api.user.UserApi;
import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.contant.AuthConstant;
import com.dj.mall.model.statement.Statement;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.order.api.OrderApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@RestController
@RequestMapping("/statement/")
public class StatementController {
    /**
     * 用户api
     */
    @Reference
    private UserApi userApi;
    /**
     * 用户api
     */
    @Reference
    private OrderApi orderApi;


    /**
     * 获取每日用户登录量
     * @return  userLoginNum
     * @throws Exception 异常
     */
    @GetMapping("getUserLoginNum")
    public ResultModel<Object> getUserLoginNum(Integer january) throws Exception {
        return new ResultModel<>().success(userApi.getUserLoginNum(january));
    }

    /**
     * 近七日订单总成交量
     * @param user 登录用户
     * @return  ResultModel
     * @throws Exception 异常
     */
    @GetMapping("getOrderTotalVolume")
    public ResultModel<Object> getOrderTotalVolume(@SessionAttribute(AuthConstant.SESSION_USER) UserDTO user) throws Exception {
        return new ResultModel<>().success(orderApi.getOrderTotalVolume(user.getUserRole(), user.getUserId()));
    }

    /**
     * 订单商品分类
     * @param user 登录用户
     * @return  ResultModel
     * @throws Exception 异常
     */
    @GetMapping("getOrderStatusByProduct")
    public ResultModel<Object> getOrderStatusByProduct(@SessionAttribute(AuthConstant.SESSION_USER) UserDTO user) throws Exception {
        return new ResultModel<>().success(orderApi.getOrderStatusByProduct(user.getUserRole(), user.getUserId()));
    }
}
