/*
 * 作者：CF
 * 日期：2020-08-03 17:01
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：OrderController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.web.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.order.OrderInfoVoReq;
import com.dj.mall.admin.vo.order.OrderInfoVoResp;
import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.contant.AuthConstant;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.order.api.OrderApi;
import com.dj.mall.order.dto.OrderInfoDTO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/order/")
public class OrderController {
    @Reference
    private OrderApi orderApi;

    /**
     * 订单展示
     * @param orderInfoVoReq 子订单req
     * @param userDTO  用户session
     * @return pageResult
     * @throws Exception 异常
     */
    @GetMapping("show")
    @RequiresPermissions(value = PermissionsCode.ORDER_MANAGE)
    public ResultModel<Object> show(OrderInfoVoReq orderInfoVoReq, @SessionAttribute(AuthConstant.SESSION_USER) UserDTO userDTO) throws Exception {
        orderInfoVoReq.setUserId(userDTO.getUserId()).setRoleId(userDTO.getUserRole());
        PageResult pageResult = orderApi.adminShow(DozerUtil.map(orderInfoVoReq, OrderInfoDTO.class));
        return new ResultModel<>().success(pageResult.toBuilder().list(DozerUtil.mapList(pageResult.getList(), OrderInfoVoResp.class)).build());
    }

    /**
     * 修改订单状态
     * @param orderNo 订单号
     * @param orderStatus 订单状态
     * @return ResultModel
     * @throws Exception 异常
     */
    @PutMapping("updateStatus")
    public ResultModel<Object> updateStatus(String orderNo, String orderStatus) throws Exception {
        orderApi.updateStatus(orderNo, orderStatus,null);
        return new ResultModel<>().success(true);
    }

    /**
     * 提醒发货
     * @param orderNo 订单号
     * @param remindStatus 提醒状态 1
     * @return  ResultModel
     * @throws Exception 异常
     */
    @PutMapping("updateRemind")
    public ResultModel<Object> updateRemind(String orderNo, Integer remindStatus) throws Exception {
        orderApi.updateRemind(orderNo, remindStatus);
        return new ResultModel<>().success(true);
    }

    /**
     * 商户消息提醒
     * @return  提醒条数
     * @throws Exception  异常
     */
    @GetMapping("showInform")
    public ResultModel<Object> showInform(@SessionAttribute(AuthConstant.SESSION_USER) UserDTO userDTO) throws Exception {
        return new ResultModel<>().success(DozerUtil.mapList(orderApi.showInform(userDTO.getUserId()), OrderInfoVoResp.class));
    }

    /**
     * 导出订单
     * @param orderInfoVoReq orderInfoVoReq
     * @param userDTO 当前登录用户
     * @param response  HttpServletResponse
     * @return ResultModel
     * @throws Exception 异常
     */
    @RequestMapping("exportOrder")
    public ResultModel<Object> showInform(OrderInfoVoReq orderInfoVoReq, @SessionAttribute(AuthConstant.SESSION_USER) UserDTO userDTO, HttpServletResponse response) throws Exception {
        //得到当前登录用户id以及用户角色
        orderInfoVoReq.setUserId(userDTO.getUserId()).setRoleId(userDTO.getUserRole());
        byte[] workbook = orderApi.exportOrder(DozerUtil.map(orderInfoVoReq, OrderInfoDTO.class));
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "application/octet-stream;charset=UTF-8");
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("订单导出.xlsx", "UTF-8"));
        response.getOutputStream().write(workbook);
        return new ResultModel<>().success(DozerUtil.mapList(orderApi.showInform(userDTO.getUserId()), OrderInfoVoResp.class));
    }
}
