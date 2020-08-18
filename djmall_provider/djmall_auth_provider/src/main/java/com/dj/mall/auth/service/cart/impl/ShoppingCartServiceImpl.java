/*
 * 作者：CF
 * 日期：2020-07-16 15:35
 * 项目：djmall
 * 模块：djmall_auth_provider
 * 类名：ShoppingCartServiceImpl
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.service.cart.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.dto.cart.ShoppingCartDTO;
import com.dj.mall.auth.entity.cart.ShoppingCartEntity;
import com.dj.mall.auth.mapper.cart.ShoppingCartMapper;
import com.dj.mall.auth.service.cart.ShoppingCartService;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chengf
 * @date 2020/7/16 15:36
 * 购物车
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCartEntity> implements ShoppingCartService {
}
