/*
 * 作者：CF
 * 日期：2020-07-16 15:35
 * 项目：djmall
 * 模块：djmall_auth_provider
 * 类名：ShoppingCartService
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.mapper.cart;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.auth.bo.cart.ShoppingCartBO;
import com.dj.mall.auth.entity.cart.ShoppingCartEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengf
 * @date 2020/7/16 15:36
 * 购物车
 */
public interface ShoppingCartMapper extends BaseMapper<ShoppingCartEntity> {
    /**
     * 去我的购物车
     * @param userId 用户id
     * @return 购物车BO
     * @throws DataAccessException 异常
     */
    List<ShoppingCartBO> findCartByToken(Integer userId) throws DataAccessException;

    /**
     * 金额计算
     * @param ids 购物车id集合
     * @return 购物车BO
     * @throws DataAccessException 异常
     */
    List<ShoppingCartBO> findPriceByCartId(@Param("ids") ArrayList<Integer> ids) throws DataAccessException;

    /**
     * 根据购物车id查
     * @param cartId 购物车id查
     * @return 购物车BO
     * @throws DataAccessException 异常
     */
    ShoppingCartBO findCartByCartId(Integer cartId) throws DataAccessException;
}
