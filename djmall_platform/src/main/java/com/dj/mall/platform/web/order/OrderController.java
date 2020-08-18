/*
 * 作者：CF
 * 日期：2020-07-30 15:47
 * 项目：djmall
 * 模块：djmall_platform
 * 类名：OrderController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.platform.web.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.contant.OrderConstant;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.order.api.OrderApi;
import com.dj.mall.order.dto.OrderDTO;
import com.dj.mall.platform.vo.order.OrderInfoVoResp;
import com.dj.mall.platform.vo.order.OrderVoReq;
import com.dj.mall.platform.vo.order.OrderVoResp;
import org.springframework.web.bind.annotation.*;

/**
 * @author chengf
 * @date 2020/7/30 15:48
 * 订单
 */
@RestController
@RequestMapping("/order/")
public class OrderController {
    /**
     * 主订单api
     */
    @Reference
    private OrderApi orderApi;

    /**
     *
     * @param orderVoReq OrderVoReq
     * @param TOKEN 令牌密钥 用户唯一标识
     * @return ResultModel
     * @throws Exception 异常
     */
    @PostMapping("addOrder")
    public ResultModel<Object> addOrder(OrderVoReq orderVoReq, String TOKEN) throws Exception {
        orderApi.addOrder(DozerUtil.map(orderVoReq, OrderDTO.class), TOKEN);
        return new ResultModel<>().success();
    }

    /**
     * 查找主订单的全部数据
     * @param orderVoReq orderDTO
     * @param TOKEN 令牌密钥 用户唯一标识
     * @return  ResultModel
     * @throws Exception 异常
     */
    @PostMapping("showOrder")
    public ResultModel<Object> showOrder(OrderVoReq orderVoReq, @RequestHeader(OrderConstant.TOKEN) String TOKEN) throws Exception {
        PageResult pageResult = orderApi.findOrderAll(DozerUtil.map(orderVoReq, OrderDTO.class), TOKEN);
        return new ResultModel<>().success(pageResult.toBuilder().list(DozerUtil.mapList(pageResult.getList(), OrderVoResp.class)).build());
    }

    /**
     *待收货/已完成/已取消订单展示
     * @param pageNo 起始页
     * @param orderStatus 订单状态
     * @param TOKEN 令牌密钥 用户唯一标识
     * @return ResultModel
     * @throws Exception 异常
     */
    @PostMapping("getOrderInfo")
    public ResultModel<Object> getOrderInfo(Integer pageNo, String orderStatus, @RequestHeader(OrderConstant.TOKEN) String TOKEN) throws Exception {
        PageResult pageResult = orderApi.getOrderInfo(pageNo, orderStatus, TOKEN);
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
        orderApi.updateStatus(orderNo, orderStatus);
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
     * 加入购物车 再次购买
     * @param orderNo 订单号
     * @return ResultModel
     * @throws Exception 异常
     */
    @PostMapping("buyAgain")
    public ResultModel<Object> buyAgain(String orderNo) throws Exception {
        orderApi.addCart(orderNo);
        return new ResultModel<>().success(true);
    }
}
