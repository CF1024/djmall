package com.dj.mall.dict.api.attr;

import com.dj.mall.dict.dto.attr.AttrValueDTO;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.PageResult;

/**
 * @author chengf
 * @date 2020/6/28 14:56
 * 商品属性值
 */
public interface AttrValueApi {
    /**
     * 展示商品属性值
     * @param attrValueDTO
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    PageResult findAll(AttrValueDTO attrValueDTO) throws Exception, BusinessException;

    /**
     * 去重
     * @param attrValue
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    Boolean deDuplicate(String attrValue) throws Exception, BusinessException;

    /**
     * 新增
     * @param attrValueDTO
     * @throws Exception
     * @throws BusinessException
     */
    void addAttrValue(AttrValueDTO attrValueDTO) throws Exception, BusinessException;

    /**
     * 删除
     * @param attrValueId
     * @throws Exception
     * @throws BusinessException
     */
    void deleteAttrValue(Integer attrValueId) throws Exception, BusinessException;
}
