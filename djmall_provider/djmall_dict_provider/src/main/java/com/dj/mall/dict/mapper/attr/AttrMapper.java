/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_dict_provider
 * 类名：AttrMapper
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.dict.mapper.attr;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.dict.bo.attr.AttrBO;
import com.dj.mall.dict.entity.attr.AttrEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/27 20:56
 * 商品属性
 */
public interface AttrMapper extends BaseMapper<AttrEntity> {
    /**
     * 展示商品属性
     * @return
     * @throws DataAccessException
     */
    List<AttrBO> findAll() throws DataAccessException;

    /**
     * 加载通用sku已关联的商品属性根据商品类型查
     * @param productType 商品类型
     * @return
     * @throws DataAccessException
     */
    List<AttrBO> loadSkuGmRelatedAttrByProductType(String productType) throws DataAccessException;
}
