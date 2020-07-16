/*
 * 作者：CF
 * 日期：2020-07-07 09:50
 * 项目：djmall
 * 模块：djmall_product_api
 * 类名：SkuApi
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.product.api.sku;

import com.dj.mall.model.base.BusinessException;
import com.dj.mall.product.dto.sku.SkuDTO;

import java.util.List;

/**
 * @author chengf
 * @date 2020/7/7 9:51
 * 商品sku
 */
public interface SkuApi {

    /**
     * 根据商品Id查找对应已关联的sku数据
     * @param productId 商品Id
     * @return List<SkuDTO>集合
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    List<SkuDTO> findSkuByProductId(Integer productId) throws Exception, BusinessException;

    /**
     * 根据商品skuId查询商品sku数据
     * @param id 商品skuId
     * @return SkuDTO
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    SkuDTO findSkuBySkuId(Integer id) throws Exception, BusinessException;

    /**
     * 根据skuId修改商品sku
     * @param skuDTO skuDTO
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    void updateSkuById(SkuDTO skuDTO) throws Exception, BusinessException;

    /**
     * 根据ID设为默认 以及将原本是默认的修改为不是默认
     * @param skuId 商品skuId
     * @param productId 商品Id
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    void updateIsDefaultById(Integer skuId, Integer productId) throws Exception, BusinessException;

    /**
     * 根据ID修改上下架状态
     * @param skuId 商品skuId
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    void updateStatusById(Integer skuId) throws Exception, BusinessException;

    /**
     * 根据商品skuId查找sku数据
     * @param skuId 商品skuId
     * @return SkuDTO
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    SkuDTO getSkuBySkuId(Integer skuId) throws Exception, BusinessException;
}
