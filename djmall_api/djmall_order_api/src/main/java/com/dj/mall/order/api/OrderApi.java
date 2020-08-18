/*
 * 作者：CF
 * 日期：2020-07-29 18:06
 * 项目：djmall
 * 模块：djmall_order_api
 * 类名：OrderApi
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.order.api;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.order.dto.OrderDTO;
import com.dj.mall.order.dto.OrderDetailDTO;
import com.dj.mall.order.dto.OrderInfoDTO;

import java.util.List;

/**
 * @author chengf
 * @date 2020/7/29 18:25
 * 主订单
 */
public interface OrderApi {
    /**
     * 新增订单
     * @param orderDTO orderDTO
     * @param TOKEN 令牌密钥 用户唯一标识
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    void addOrder(OrderDTO orderDTO, String TOKEN) throws Exception, BusinessException;

    /**
     * 查找主订单的全部数据
     * @param orderDTO orderDTO
     * @param TOKEN 令牌密钥 用户唯一标识
     * @return  PageResult
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    PageResult findOrderAll(OrderDTO orderDTO, String TOKEN) throws Exception, BusinessException;

    /**
     *待收货/已完成/已取消订单展示
     * @param pageNo 起始页
     * @param orderStatus 订单状态
     * @param TOKEN 令牌密钥 用户唯一标识
     * @return ResultModel
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    PageResult getOrderInfo(Integer pageNo, String orderStatus, String TOKEN) throws Exception, BusinessException;

    /**
     * 主订单详情展示
     * @param orderNo 订单号
     * @return OrderDTO
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    OrderDTO findOrderByOrderNo(String orderNo) throws Exception, BusinessException;

    /**
     * 子订单详情展示
     * @param orderNo 订单号
     * @return OrderDTO
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    OrderInfoDTO findOrderInfoByOrderNo(String orderNo) throws Exception, BusinessException;

    /**
     * 修改订单状态 取消订单
     * @param orderNo 订单号
     * @param orderStatus 订单状态
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    void updateStatus(String orderNo, String orderStatus) throws Exception, BusinessException;

    /**
     * 提醒发货
     * @param orderNo 订单号
     * @param remindStatus 提醒状态 1 以提醒
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    void updateRemind(String orderNo, Integer remindStatus) throws Exception, BusinessException;

    /**
     * 再次购买
     * @param orderNo 订单号
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    void addCart(String orderNo) throws Exception, BusinessException;

    /**
     * admin订单展示
     * @param orderInfoDTO 订单dto
     * @return PageResult
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    PageResult adminShow(OrderInfoDTO orderInfoDTO) throws Exception, BusinessException;

    /**
     * 订单评论
     * @param orderNo 子订单号
     * @return OrderDetailDTO集合数据
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    List<OrderDetailDTO> findOrderDetailByChildOrderNo(String orderNo) throws Exception, BusinessException;

    /**
     * 商户登录消息提醒
     * @return List<OrderDetailDTO>
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    List<OrderInfoDTO> showInform(Integer userId) throws Exception, BusinessException;

    /**
     * 导出订单
     * @param orderInfoDTO orderInfoDTO
     * @return  byte[]
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    byte[] exportOrder(OrderInfoDTO orderInfoDTO) throws Exception, BusinessException;

    /**
     * 修改订单评论状态
     * @param detailIdList 明细订单id
     * @param isComment 订单评论状态 1 已评论
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    void updateDetailComment(List<Integer> detailIdList, Integer isComment) throws Exception, BusinessException;

    /**
     * 根据子订单号查该订单得评论是否全部完成
     * @param orderNo 子订单号
     * @return  Integer
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    Integer findAllIsCommentByChildOrderNo(String orderNo) throws Exception, BusinessException;
}
