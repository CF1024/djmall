/*
 * 作者：CF
 * 日期：2020-07-29 18:06
 * 项目：djmall
 * 模块：djmall_order_api
 * 类名：OrderApi
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.order.entity.OrderDetailEntity;
import com.dj.mall.order.mapper.OrderDetailMapper;
import com.dj.mall.order.service.OrderDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chengf
 * @date 2020/7/29 18:25
 * 明细订单
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetailEntity> implements OrderDetailService {
}
