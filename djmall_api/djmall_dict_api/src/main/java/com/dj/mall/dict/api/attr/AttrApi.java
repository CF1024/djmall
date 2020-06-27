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
}
