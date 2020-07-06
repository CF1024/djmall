/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_dict_api
 * 类名：AttrApi
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.dict.api.attr;

import com.dj.mall.dict.dto.attr.AttrDTO;
import com.dj.mall.model.base.BusinessException;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/27 20:56
 * 商品属性
 */
public interface AttrApi {
    /**
     * 展示商品属性
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    List<AttrDTO> findAll() throws Exception, BusinessException;

    /**
     * 去重
     * @param attrId
     * @param attrName
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    Boolean deDuplicate(Integer attrId, String attrName) throws Exception, BusinessException;

    /**
     * 新增
     * @param attrDTO
     * @throws Exception
     * @throws BusinessException
     */
    void addAttr(AttrDTO attrDTO) throws Exception, BusinessException;

    /**
     * 根据id查 去关联属性值
     * @param attrId
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    AttrDTO findAttrById(Integer attrId) throws Exception, BusinessException;

    /**
     * 加载通用sku已关联的商品属性
     * @param productType 商品类型
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    List<AttrDTO> loadSkuGmRelatedAttr(String productType) throws Exception, BusinessException;
}
