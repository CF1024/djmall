/*
 * 作者：CF
 * 日期：2020-07-29 18:06
 * 项目：djmall
 * 模块：djmall_order_api
 * 类名：OrderApi
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.mall.model.statement.Statement;
import com.dj.mall.order.bo.OrderBO;
import com.dj.mall.order.bo.OrderInfoBO;
import com.dj.mall.order.entity.OrderEntity;
import com.dj.mall.order.entity.OrderInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author chengf
 * @date 2020/7/29 18:25
 * 主订单
 */
public interface OrderMapper extends BaseMapper<OrderEntity> {
    /**
     * 查找主订单的全部数据
     * @param page 起始页 每页条数
     * @param userId 用户id
     * @return   List<OrderBO>
     * @throws DataAccessException 异常
     */
    IPage<OrderBO> findOrderAll(Page<OrderEntity> page, @Param("userId") Integer userId) throws DataAccessException;

    /**
     * 根据用户id和订单状态查找子订单数据
     * @param page 起始页
     * @param orderStatus 订单状态
     * @param userId 用户id
     * @return   List<OrderInfoBO>
     * @throws DataAccessException 异常
     */
    IPage<OrderInfoBO> getOrderInfoByUserIdAndOrderStatus(@Param("page") Page<OrderEntity> page, @Param("orderStatus") String orderStatus, @Param("userId") Integer userId) throws DataAccessException;

    /**
     * 根据订单号查订单明细表
     * @param orderNo 订单号
     * @return OrderBO
     * @throws DataAccessException 异常
     */
    OrderBO findOrderByOrderNo(String orderNo) throws DataAccessException;

    /**
     * 子订单详情
     * @param orderNo 订单号
     * @return OrderInfoBO
     * @throws DataAccessException 异常
     */
    OrderInfoBO findOrderInfoByOrderNo(String orderNo) throws DataAccessException;

    /**
     * 修改sku库存
     * @param orderNo 订单号
     * @throws DataAccessException 异常
     */
    void updateCount(String orderNo) throws DataAccessException;

    /**
     * admin订单展示
     * @param page 分页
     * @param orderInfoBO orderInfoBO
     * @return IPage
     * @throws DataAccessException 异常
     */
    IPage<OrderInfoBO> adminShow(Page<OrderInfoEntity> page, @Param("order") OrderInfoBO orderInfoBO) throws DataAccessException;

    /**
     * 根据子订单号查该订单得评论是否全部完成
     * @param orderNo 子订单号
     * @return Integer
     * @throws DataAccessException 异常
     */
    Integer findAllIsCommentByChildOrderNo(String orderNo) throws DataAccessException;

    /**
     * 近七日订单总成交量
     * @param roleId 当前登录用户id
     * @return List<Statement>
     * @throws DataAccessException 异常
     */
    List<Statement> getOrderTotalVolume(@Param("roleId") Integer roleId, @Param("userId") Integer userId) throws DataAccessException;

    /**
     * 订单商品分类
     * @param roleId 角色
     * @param userId 用户id
     * @return  List<Statement>
     * @throws DataAccessException 异常
     */
    List<Statement> getOrderStatusByProduct(@Param("roleId") Integer roleId, @Param("userId") Integer userId) throws DataAccessException;
}
