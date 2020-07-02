package com.dj.mall.product.pro.spu;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.product.api.spu.ProductApi;
import com.dj.mall.product.entity.spu.ProductEntity;
import com.dj.mall.product.mapper.spu.ProductMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chengf
 * @date 2020/7/2 15:16
 * 商品ApiImpl
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductApiImpl extends ServiceImpl<ProductMapper, ProductEntity> implements ProductApi {
}
