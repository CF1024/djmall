package com.dj.mall.product.api.spu;

import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.product.dto.spu.ProductDTO;

/**
 * @author chengf
 * @date 2020/7/2 15:16
 * 商品Api
 */
public interface ProductApi {
    /**
     * 商品展示
     * @param productDTO 商品dto
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    PageResult findAll(ProductDTO productDTO) throws Exception, BusinessException;
    /**
     * 去重
     * @param productDTO
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    Boolean deDuplicate(ProductDTO productDTO) throws Exception, BusinessException;

    /**
     * 商品新增
     * @param productDTO
     * @param file
     * @throws Exception
     * @throws BusinessException
     */
    void addProduct(ProductDTO productDTO, byte[] file) throws Exception, BusinessException;


}
