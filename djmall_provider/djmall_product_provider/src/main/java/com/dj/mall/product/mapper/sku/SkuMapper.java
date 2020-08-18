/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_product_provider
 * 类名：SkuMapper
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.product.mapper.sku;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.product.bo.sku.SkuBO;
import com.dj.mall.product.entity.sku.SkuEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author chengf
 * @date 2020/7/4 19:10
 * 商品sku
 */
public interface SkuMapper extends BaseMapper<SkuEntity> {

    List<SkuBO> findSkuBySkuIds(@Param("list") List<Integer> skuIdList) throws DataAccessException;
}
