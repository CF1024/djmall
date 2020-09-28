/*
 * 作者：CF
 * 日期：2020-07-16 17:04
 * 项目：djmall
 * 模块：djmall_auth_api
 * 类名：ShoppingCartApi
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.api.cart;

import com.dj.mall.auth.dto.cart.ShoppingCartDTO;
import com.dj.mall.model.base.BusinessException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengf
 * @date 2020/7/16 17:05
 * 购物车
 */
public interface ShoppingCartApi {
    /**
     * 去我的购物车
     * @param TOKEN 令牌密钥 用户唯一标识
     * @return 购物车dto集合
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    List<ShoppingCartDTO> findCartByToken(String TOKEN) throws Exception, BusinessException;

    /**
     * 计算金额
     * @param ids 购物车id集合
     * @return ShoppingCartDTO
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    ShoppingCartDTO getTotal(ArrayList<Integer> ids, String TOKEN) throws Exception, BusinessException;

    /**
     * 根据购物车id修改购物车
     * @param shoppingCartDTO 购物车dto
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    void updateCart(ShoppingCartDTO shoppingCartDTO) throws Exception, BusinessException;

    /**
     *根据购物车id删除购物车
     * @param ids  购物车id集合
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    void deleteCartById(Integer[] ids) throws Exception, BusinessException;

    /**
     * 根据购物车id查
     * @param cartId 购物车id查
     * @return ShoppingCartDTO
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    ShoppingCartDTO findCartByCartId(Integer cartId) throws Exception, BusinessException;
}
