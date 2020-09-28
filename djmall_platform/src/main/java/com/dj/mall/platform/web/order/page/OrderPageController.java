/*
 * 作者：CF
 * 日期：2020-07-30 15:47
 * 项目：djmall
 * 模块：djmall_platform
 * 类名：OrderController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.platform.web.order.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.order.api.OrderApi;
import com.dj.mall.platform.vo.order.OrderDetailVoResp;
import com.dj.mall.platform.vo.order.OrderInfoVoResp;
import com.dj.mall.platform.vo.order.OrderVoResp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @author chengf
 * @date 2020/7/30 15:48
 * 订单
 */
@Controller
@RequestMapping("/order/")
public class OrderPageController {
    @Reference
    private OrderApi orderApi;
    /**
     * 订单展示
     * @return 展示页
     */
    @GetMapping("toShow")
    public String toShow() {
        return "order/show";
    }

    /**
     * 主订单展示商品详情
     * @param orderNo 订单号
     * @param model ModelMap
     * @return 展示商品详情
     * @throws Exception 异常
     */
    @GetMapping("toOrderDetails")
    public String toOrderDetails(String orderNo, ModelMap model) throws Exception {
        OrderVoResp orderVoResp = DozerUtil.map(orderApi.findOrderByOrderNo(orderNo), OrderVoResp.class);
        String phone = orderVoResp.getReceiverPhone();
        String phoneNumber = phone.substring(0, 3) + "****" + phone.substring(7, phone.length());
        model.put("order", orderVoResp);
        model.put("phoneNumber", phoneNumber);
        return "order/order_detail";
    }

    /**
     * 子订单展示商品详情
     * @param orderNo 订单号
     * @param model ModelMap
     * @return 展示商品详情
     * @throws Exception 异常
     */
    @GetMapping("toOrderInfoDetails")
    public String toOrderInfoDetails(String orderNo, ModelMap model) throws Exception {
        OrderInfoVoResp orderInfoVoResp = DozerUtil.map(orderApi.findOrderInfoByOrderNo(orderNo), OrderInfoVoResp.class);
        String phone = orderInfoVoResp.getReceiverPhone();
        String phoneNumber = phone.substring(0, 3) + "****" + phone.substring(7, phone.length());
        model.put("order", orderInfoVoResp);
        model.put("phoneNumber", phoneNumber);
        return "order/order_detail";
    }

    /**
     * 去评论
     * @param orderNo 子订单号
     * @param model  ModelMap
     * @return 评论页
     * @throws Exception 异常
     */
    @GetMapping("toComment")
    public String toComment(String orderNo, ModelMap model) throws Exception {
        model.put("orderNo", orderNo);
        model.put("productList", DozerUtil.mapList(orderApi.findOrderDetailByChildOrderNo(orderNo), OrderDetailVoResp.class));
        return "order/comment";
    }

    /**
     * 支付宝同步回调
     * @return 回调前台 success
     */
    @RequestMapping("aliPaySuccess")
    public String aliPaySuccess(){
        return "order/aliPaySuccess";
    }
}
