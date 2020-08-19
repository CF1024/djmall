/*
 * 作者：CF
 * 日期：2020-07-29 18:06
 * 项目：djmall
 * 模块：djmall_order_api
 * 类名：OrderApi
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.statement.Statement;
import com.dj.mall.order.dto.OrderInfoDTO;
import com.dj.mall.order.entity.OrderInfoEntity;

import java.util.List;

/**
 * @author chengf
 * @date 2020/7/29 18:25
 * 子订单
 */
public interface OrderInfoService extends IService<OrderInfoEntity> {
}
