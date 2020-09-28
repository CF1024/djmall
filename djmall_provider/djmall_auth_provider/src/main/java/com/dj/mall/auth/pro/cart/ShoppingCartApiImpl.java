/*
 * 作者：CF
 * 日期：2020-07-16 17:04
 * 项目：djmall
 * 模块：djmall_auth_api
 * 类名：ShoppingCartApi
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.pro.cart;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.api.cart.ShoppingCartApi;
import com.dj.mall.auth.bo.cart.ShoppingCartBO;
import com.dj.mall.auth.dto.cart.ShoppingCartDTO;
import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.auth.entity.cart.ShoppingCartEntity;
import com.dj.mall.auth.mapper.cart.ShoppingCartMapper;
import com.dj.mall.cmpt.RedisApi;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.contant.AuthConstant;
import com.dj.mall.model.contant.RedisConstant;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chengf
 * @date 2020/7/16 17:05
 * 购物车
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ShoppingCartApiImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCartEntity> implements ShoppingCartApi {
    @Reference
    private RedisApi redisApi;
    /**
     * 去我的购物车
     * @param TOKEN 令牌密钥 用户唯一标识
     * @return 购物车dto集合
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public List<ShoppingCartDTO> findCartByToken(String TOKEN) throws Exception, BusinessException {
        UserDTO userDTO = redisApi.get(RedisConstant.USER_TOKEN + TOKEN);
        return DozerUtil.mapList( getBaseMapper().findCartByToken(userDTO.getUserId()), ShoppingCartDTO.class);
    }

    /**
     * 计算金额
     * @param  ids 购物车id集合
     * @return ShoppingCartDTO
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public ShoppingCartDTO getTotal(ArrayList<Integer> ids, String TOKEN) throws Exception, BusinessException {
        //用户信息
        UserDTO userDTO = redisApi.get(RedisConstant.USER_TOKEN + TOKEN);
        if (ids.get(0) == -1) {
            this.update(new UpdateWrapper<ShoppingCartEntity>().set("checked", AuthConstant.CART_CHECKED_ONE).eq("user_id", userDTO.getUserId()));
            return new ShoppingCartDTO();
        }
        this.update(new UpdateWrapper<ShoppingCartEntity>().set("checked", AuthConstant.ZERO).eq("user_id", userDTO.getUserId()).in("id", ids));
        this.update(new UpdateWrapper<ShoppingCartEntity>().set("checked", AuthConstant.CART_CHECKED_ONE).eq("user_id", userDTO.getUserId()).notIn("id", ids));
        //总价
        BigDecimal totalPrice = new BigDecimal("0.00");
        //总折后价
        BigDecimal totalDiscountedPrice = new BigDecimal("0.00");
        //总运费
        BigDecimal totalFreight = new BigDecimal("0.00");
        //需要支付的最终价格 总折后价 + 总运费
        BigDecimal finalPrice;

        //根据购物车id集合查到该购物车内商品的 价格 折扣 运费数据集合
        List<ShoppingCartDTO> shoppingCartDTOList = DozerUtil.mapList(getBaseMapper().findPriceByCartId(ids), ShoppingCartDTO.class);

        //遍历集合 进行金额计算
        for (ShoppingCartDTO cart : shoppingCartDTOList) {
            //总价 = 商品单价 * 购买数量 + 总价 保留两位小数 四舍五入
            totalPrice = cart.getSkuPrice().multiply(new BigDecimal(cart.getQuantity())).add(totalPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
            //总折后价 = 商品折后单价 * 购买数量 + 总折后价 保留两位小数 四舍五入
            totalDiscountedPrice = cart.getDiscountPrice().multiply(new BigDecimal(cart.getQuantity())).add(totalDiscountedPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
            //总运费 = 总运费 + 运费  保留两位小数 四舍五入
            totalFreight = totalFreight.add(cart.getFreight()).setScale(2, BigDecimal.ROUND_HALF_UP);

        }
        //最终价格 = 总折后价 + 总运费
        finalPrice = totalDiscountedPrice.add(totalFreight);
        //总价 总折后价 总运费 最终价格
        return new ShoppingCartDTO().toBuilder().totalPrice(totalPrice).totalDiscountedPrice(totalDiscountedPrice).totalFreight(totalFreight).finalPrice(finalPrice).build();
    }

    /**
     * 根据购物车id修改购物车
     * @param shoppingCartDTO 购物车dto
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public void updateCart(ShoppingCartDTO shoppingCartDTO) throws Exception, BusinessException {
        this.updateById(DozerUtil.map(shoppingCartDTO, ShoppingCartEntity.class));
    }

    /**
     *根据购物车id删除购物车
     * @param ids  购物车id集合
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public void deleteCartById(Integer[] ids) throws Exception, BusinessException {
      this.removeByIds(Arrays.asList(ids));
    }

    /**
     * 根据购物车id查
     * @param cartId 购物车id查
     * @return ShoppingCartDTO
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public ShoppingCartDTO findCartByCartId(Integer cartId) throws Exception, BusinessException {
        return DozerUtil.map(baseMapper.findCartByCartId(cartId), ShoppingCartDTO.class);
    }
}
