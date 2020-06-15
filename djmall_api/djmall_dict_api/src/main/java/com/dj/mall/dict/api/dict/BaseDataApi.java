package com.dj.mall.dict.api.dict;

import com.dj.mall.dict.dto.dict.BaseDataDTO;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.PageResult;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/15 15:26
 * 字典数据
 */
public interface BaseDataApi {
    /**
     * 查询全部
     * @param baseDataDTO
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    PageResult findAll(BaseDataDTO baseDataDTO) throws Exception, BusinessException;

    /**
     * 根据父级名称查基础数据
     * @param parentCode
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    List<BaseDataDTO> findBaseDataByParentCode(String parentCode) throws Exception, BusinessException;

    /**
     * 去重
     * @param baseDataDTO
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    Boolean deDuplicate(BaseDataDTO baseDataDTO) throws Exception, BusinessException;

    /**
     * 新增
     * @param baseDataDTO
     * @throws Exception
     * @throws BusinessException
     */
    void addBase(BaseDataDTO baseDataDTO) throws Exception, BusinessException;

    /**
     * 根据基础编码去查基础数据
     * @param baseCode 基础编码
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    BaseDataDTO findBaseDataByBaseCode(String baseCode) throws Exception, BusinessException;

    /**
     * 修改
     * @param baseDataDTO
     * @throws Exception
     * @throws BusinessException
     */
    void updateBase(BaseDataDTO baseDataDTO) throws Exception, BusinessException;
}
