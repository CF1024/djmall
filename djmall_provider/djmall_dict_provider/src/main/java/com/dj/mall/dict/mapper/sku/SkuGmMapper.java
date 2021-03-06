/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_dict_provider
 * 类名：SkuGmMapper
 * 版权所有(C), 2020. 所有权利保留
 */

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
    IPage<SkuGmBO> findAll(Page<SkuGmEntity> page, @Param("skuGm") SkuGmBO skuGmBO) throws DataAccessException;

    /**
     * 单纯查数据
     * @return
     * @throws DataAccessException
     */
    List<SkuGmBO> findSkuGm()throws DataAccessException;
}
