/*
 * 作者：CF
 * 日期：2020-08-14 20:30
 * 项目：djmall
 * 模块：djmall_product_provider
 * 类名：ProductLikeService
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.product.service.like.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.product.entity.like.ProductLikeEntity;
import com.dj.mall.product.mapper.like.ProductLikeMapper;
import com.dj.mall.product.service.like.ProductLikeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author chengf
 * @date 2020/8/14 20:33
 * 点赞service实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductLikeServiceImpl extends ServiceImpl<ProductLikeMapper, ProductLikeEntity> implements ProductLikeService {
}
