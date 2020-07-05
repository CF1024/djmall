package com.dj.mall.product.mapper.spu;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.mall.product.bo.spu.ProductBO;
import com.dj.mall.product.entity.spu.ProductEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author chengf
 * @date 2020/7/2 15:16
 * 商品mapper
 */
public interface ProductMapper extends BaseMapper<ProductEntity> {
    /**
     * 展示
     * @param page
     * @param productBO
     * @return
     * @throws DataAccessException
     */
    IPage<ProductBO> findAll(@Param("page") Page<?> page, @Param("product") ProductBO productBO) throws DataAccessException;
}
