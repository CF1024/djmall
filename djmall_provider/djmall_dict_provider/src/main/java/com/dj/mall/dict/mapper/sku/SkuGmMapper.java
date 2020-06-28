package com.dj.mall.dict.mapper.sku;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.mall.dict.bo.sku.SkuGmBO;
import com.dj.mall.dict.entity.sku.SkuGmEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/28 18:13
 * 通用sku维护
 */
public interface SkuGmMapper extends BaseMapper<SkuGmEntity> {
    /**
     * 展示通用sku维护
     * @param page
     * @param skuGmBO
     * @return
     * @throws DataAccessException
     */
    IPage<SkuGmBO> findAll(@Param("page") Page<?> page, @Param("skuGm") SkuGmBO skuGmBO) throws DataAccessException;
}
