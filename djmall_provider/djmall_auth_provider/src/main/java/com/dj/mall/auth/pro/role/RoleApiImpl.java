/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_provider
 * 类名：RoleApiImpl
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.pro.role;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.api.resource.ResourceApi;
import com.dj.mall.auth.api.role.RoleApi;
import com.dj.mall.auth.dto.resource.ResourceDTO;
import com.dj.mall.auth.dto.resource.TreeData;
import com.dj.mall.auth.dto.role.RoleDTO;
import com.dj.mall.auth.entity.role.Role;
import com.dj.mall.auth.entity.role.RoleResource;
import com.dj.mall.auth.entity.user.User;
import com.dj.mall.auth.entity.user.UserRole;
import com.dj.mall.auth.mapper.role.RoleMapper;
import com.dj.mall.auth.mapper.role.RoleResourceMapper;
import com.dj.mall.auth.mapper.user.UserRoleMapper;
import com.dj.mall.auth.service.role.RoleResourceService;
import com.dj.mall.auth.service.user.UserRoleService;
import com.dj.mall.cmpt.RedisApi;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.contant.DictConstant;
import com.dj.mall.model.contant.RedisConstant;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengf
 * @date 2020/6/3 23:05
 * 角色api实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleApiImpl extends ServiceImpl<RoleMapper, Role> implements RoleApi {
    /**
     * 资源api
     */
    @Autowired
    private ResourceApi resourceApi;
    /**
     *用户角色service
     */
    @Autowired
    private UserRoleService userRoleService;
    /**
     *角色资源service
     */
    @Autowired
    private RoleResourceService roleResourceService;
    /**
     *redis的api
     */
    @Reference
    private RedisApi redisApi;
    /**
     * 角色展示
     * @param roleDTO
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public PageResult findAll(RoleDTO roleDTO) throws Exception, BusinessException {
        IPage<Role> iPage = getBaseMapper().selectPage(
                new Page<>(roleDTO.getPageNo(), roleDTO.getPageSize()),
                new QueryWrapper<Role>().eq("is_del", DictConstant.NOT_DEL));
        PageResult pageResult = new PageResult().toBuilder()
                .pages(iPage.getPages())
                .list(DozerUtil.mapList(iPage.getRecords(), RoleDTO.class)).build();
        return pageResult;
    }
    /**
     * 去重
     *
     * @param roleName
     * @param roleId
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public Boolean deDuplicate(String roleName, Integer roleId) throws Exception, BusinessException {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (null != roleId) {
            queryWrapper.ne("id", roleId);
        }
        if (null != roleName) {
            queryWrapper.eq("role_name", roleName);
        }
        return getBaseMapper().selectOne(queryWrapper) == null;
    }

    /**
     * 新增
     *
     * @param roleDTO
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void insertRole(RoleDTO roleDTO) throws Exception, BusinessException {
        getBaseMapper().insert(DozerUtil.map(roleDTO, Role.class).toBuilder().isDel(DictConstant.NOT_DEL).build());
    }

    /**
     * 跟据id查
     * @param roleId
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public RoleDTO findById(Integer roleId) throws Exception, BusinessException {
        return DozerUtil.map(getBaseMapper().selectById(roleId), RoleDTO.class);
    }
    /**
     * 修改
     * @param roleDTO
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void updateRole(RoleDTO roleDTO) throws Exception, BusinessException {
        getBaseMapper().updateById(DozerUtil.map(roleDTO, Role.class));
    }

    /**
     * 删除角色
     * @param roleId
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void removeRole(Integer roleId) throws Exception, BusinessException {
        //修改状态 已删除
        getBaseMapper().updateById(getBaseMapper().selectOne(new QueryWrapper<Role>().eq("id", roleId)).toBuilder().isDel(DictConstant.HAVE_DEL).build());
        //删除角色对应的用户角色关系表的数据
        userRoleService.remove(new QueryWrapper<UserRole>().eq("role_id", roleId));
        //删除角色对应的角色资源关系表的数据
        roleResourceService.remove(new QueryWrapper<RoleResource>().eq("role_id", roleId));
        //删除对应的缓存
        redisApi.delHash(RedisConstant.ROLE_ALL_KEY, RedisConstant.ROLE_ID_KEY + roleId);
    }

    /**
     * 去关联资源
     *
     * @param roleId
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public List<TreeData> getRelatedResource(Integer roleId) throws Exception, BusinessException {
        //展示资源 查资源全部信息
        List<ResourceDTO> resourceList = resourceApi.findAll();
        //角色已关联资源 根据角色id查角色资源关系表全部信息
        List<RoleResource> roleResourceList = roleResourceService.list(new QueryWrapper<RoleResource>().eq("role_id", roleId));
        //组装结构
        List<TreeData> treeDataList = new ArrayList<>();
        resourceList.forEach(resource -> {
            TreeData treeData =new TreeData().toBuilder()
                    .id(resource.getResourceId())
                    .parentId(resource.getParentId())
                    .name(resource.getResourceName()).build();
            //默认勾选
            for (RoleResource roleResource : roleResourceList) {
                if (resource.getResourceId().equals(roleResource.getResourceId())) {
                    treeData.setChecked(true);
                    break;
                }
            }
            treeDataList.add(treeData);
        });
        return treeDataList;
    }

    /**
     * 关联资源 新增角色资源关系
     * @param roleDTO 资源roleDTO
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public Boolean saveRelevance(RoleDTO roleDTO) throws Exception, BusinessException {
        List<RoleResource> roleResourceList = new ArrayList<>();
        for (Integer resourceId : roleDTO.getResourceIdList()) {
            RoleResource roleResource = new RoleResource().toBuilder()
                    .resourceId(resourceId)
                    .roleId(roleDTO.getRoleId()).build();
            roleResourceList.add(roleResource);
        }
        //先删后增
        roleResourceService.remove(new QueryWrapper<RoleResource>().eq("role_id", roleDTO.getRoleId()));
        roleResourceService.saveBatch(roleResourceList);
        redisApi.pushHash(RedisConstant.ROLE_ALL_KEY, RedisConstant.ROLE_ID_KEY + roleDTO.getRoleId(), findRoleResourceBuRoleId(roleDTO.getRoleId()));
        return true;
    }

    /**
     * 根据角色ID查角色已关联资源
     * @param roleId 角色 ID
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public List<ResourceDTO> findRoleResourceBuRoleId(Integer roleId) throws Exception, BusinessException {
        return DozerUtil.mapList(getBaseMapper().findRoleResourceBuRoleId(roleId), ResourceDTO.class);
    }
}
