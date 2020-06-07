package com.dj.mall.auth.api.resource;

import com.dj.mall.auth.dto.resource.ResourceDTO;
import com.dj.mall.model.base.BusinessException;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/3 23:02
 * 资源api接口
 */
public interface ResourceApi {
    /**
     * 展示资源
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    List<ResourceDTO> findAll() throws Exception, BusinessException;

    /**
     * 根据父级id查资源 去新增
     * @param parentId
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    ResourceDTO findResourceByParentId(Integer parentId) throws Exception, BusinessException;

    /**
     * 去重
     * @param resourceDTO
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    Boolean deDuplicate(ResourceDTO resourceDTO) throws Exception, BusinessException;

    /**
     * 资源新增
     * @param resourceDTO
     * @throws Exception
     * @throws BusinessException
     */
    void insertResource(ResourceDTO resourceDTO) throws Exception, BusinessException;

    /**
     * 去修改 根据id查
     * @param resourceId
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    ResourceDTO findResourceByResourceId(Integer resourceId) throws Exception, BusinessException;

    /**
     * 修改
     * @param resourceDTO
     * @throws Exception
     * @throws BusinessException
     */
    void updateResource(ResourceDTO resourceDTO) throws Exception, BusinessException;
}
