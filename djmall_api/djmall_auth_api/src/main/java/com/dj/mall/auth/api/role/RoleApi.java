package com.dj.mall.auth.api.role;

import com.dj.mall.auth.dto.resource.ResourceDTO;
import com.dj.mall.auth.dto.resource.TreeData;
import com.dj.mall.auth.dto.role.RoleDTO;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.PageResult;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/3 23:02
 * 角色api接口
 */
public interface RoleApi {
    /**
     * 角色展示
     * @param roleDTO
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    PageResult findAll(RoleDTO roleDTO) throws Exception, BusinessException;
    /**
     * 去重
     * @param roleName 角色名
     * @param roleId 角色id
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    Boolean deDuplicate(String roleName, Integer roleId) throws Exception, BusinessException;

    /**
     * 新增
     * @param roleDTO 角色dto
     * @throws Exception
     * @throws BusinessException
     */
    void insertRole(RoleDTO roleDTO) throws Exception, BusinessException;
    /**
     * 跟据id查
     * @param roleId 角色id
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    RoleDTO findById(Integer roleId) throws Exception, BusinessException;
    /**
     * 修改
     * @param roleDTO 角色dto
     * @throws Exception
     * @throws BusinessException
     */
    void updateRole(RoleDTO roleDTO) throws Exception, BusinessException;

    /**
     * 删除角色
     * @param roleId 角色id
     * @throws Exception
     * @throws BusinessException
     */
    void removeRole(Integer roleId) throws Exception, BusinessException;

    /**
     * 去关联资源
     * @param roleId 角色id
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    List<TreeData> getRelatedResource(Integer roleId) throws Exception, BusinessException;

    /**
     * 关联资源 新增角色资源关系
     * @param roleDTO 资源roleDTO
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    Boolean saveRelevance(RoleDTO roleDTO) throws Exception, BusinessException;

    /**
     * 根据角色ID查角色已关联资源
     * @param roleId 角色 ID
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    List<ResourceDTO> findRoleResourceBuRoleId(Integer roleId) throws Exception, BusinessException;
}
