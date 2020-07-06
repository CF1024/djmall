/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_dict_api
 * 类名：SkuGmApi
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.dict.api.sku;

import com.dj.mall.dict.dto.sku.SkuGmDTO;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.PageResult;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/28 18:13
 * 通用sku维护
 */
public interface SkuGmApi {
    /**
     * 展示通用sku维护
     * @param skuGmDTO
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    PageResult findAll(SkuGmDTO skuGmDTO) throws Exception, BusinessException;

    /**
     * 根据商品类型查
     * @param  productType 商品类型
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    List<SkuGmDTO> findSkuGmByProductType(String productType) throws Exception, BusinessException;

    /**
     * 关联属性
     * @param ids
     * @param productType
     * @throws Exception
     * @throws BusinessException
     */
    void RelatedAttr(Integer[] ids, String productType) throws Exception, BusinessException;

    /**
     * 去重
     * @param productType
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    Boolean deDuplicate(String productType) throws Exception, BusinessException;

    /**
     * 新增
     * @param skuGmDTO
     * @throws Exception
     * @throws BusinessException
     */
    void addSkuGm(SkuGmDTO skuGmDTO) throws Exception, BusinessException;
}
