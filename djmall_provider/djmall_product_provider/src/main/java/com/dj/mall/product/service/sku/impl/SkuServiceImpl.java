/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_product_provider
 * 类名：SkuServiceImpl
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.product.service.sku.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.product.entity.sku.SkuEntity;
import com.dj.mall.product.mapper.sku.SkuMapper;
import com.dj.mall.product.service.sku.SkuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chengf
 * @date 2020/7/4 19:10
 * 商品sku
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SkuServiceImpl extends ServiceImpl<SkuMapper, SkuEntity> implements SkuService {
}
