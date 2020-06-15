package com.dj.mall.auth.pro.resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.api.resource.ResourceApi;
import com.dj.mall.auth.dto.resource.ResourceDTO;
import com.dj.mall.auth.entity.resource.Resource;
import com.dj.mall.auth.entity.role.RoleResource;
import com.dj.mall.auth.mapper.resource.ResourceMapper;
import com.dj.mall.auth.service.role.RoleResourceService;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.contant.AuthConstant;
import com.dj.mall.model.contant.DictConstant;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengf
 * @date 2020/6/3 23:04
 * 资源api实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceApiImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceApi {
    @Autowired
    private RoleResourceService roleResourceService;
    /**
     * 展示资源
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public List<ResourceDTO> findAll() throws Exception, BusinessException {
        //查询未删除的资源
        return DozerUtil.mapList(getBaseMapper()
                .selectList(new QueryWrapper<Resource>().eq("is_del", DictConstant.NOT_DEL)), ResourceDTO.class);
    }

    /**
     * 根据父级id查资源 去新增
     * @param parentId
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public ResourceDTO findResourceByParentId(Integer parentId) throws Exception, BusinessException {
        return DozerUtil.map(getBaseMapper().selectById(parentId), ResourceDTO.class);
    }

    /**
     * 去重
     *
     * @param resourceDTO
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public Boolean deDuplicate(ResourceDTO resourceDTO) throws Exception, BusinessException {
        Resource resource = DozerUtil.map(resourceDTO, Resource.class);
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(resource.getId())) {
            queryWrapper.ne("id", resourceDTO.getResourceId());
        }
        if (!StringUtils.isEmpty(resource.getResourceName())) {
            queryWrapper.eq("resource_name", resource.getResourceName());
        }
        if (!StringUtils.isEmpty(resource.getResourceCode())) {
            queryWrapper.eq("resource_code", resource.getResourceCode());
        }
        if (!StringUtils.isEmpty(resource.getUrl())) {
            queryWrapper.eq("url", resource.getUrl());
        }
        if (AuthConstant.URL.equals(resource.getUrl())) {
            return true;
        }
        return getBaseMapper().selectOne(queryWrapper) == null;
    }

    /**
     * 资源新增
     *
     * @param resourceDTO
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void insertResource(ResourceDTO resourceDTO) throws Exception, BusinessException {
        //默认状态为未删除， 资源编码大写
        getBaseMapper().insert(DozerUtil.map(resourceDTO.toBuilder().isDel(DictConstant.NOT_DEL)
                .resourceCode(resourceDTO.getResourceCode().toUpperCase()).build(), Resource.class));
    }

    /**
     * 去修改 根据id查
     * @param resourceId
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public ResourceDTO findResourceByResourceId(Integer resourceId) throws Exception, BusinessException {
        return DozerUtil.map(getBaseMapper().selectById(resourceId), ResourceDTO.class);
    }

    /**
     * 修改
     * @param resourceDTO
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void updateResource(ResourceDTO resourceDTO) throws Exception, BusinessException {
        getBaseMapper().updateById(DozerUtil.map(resourceDTO.toBuilder()
                .resourceCode(resourceDTO.getResourceCode().toUpperCase()).build(), Resource.class));
    }

    /**
     * 修改资源状态 已删除
     *
     * @param id
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void updateResourceIsDel(Integer id) throws Exception, BusinessException {
        List<Integer> ids = new ArrayList<>();
        ids.add(id);
        getIds(id, ids);
        getBaseMapper().updateResourceIsDelByIds(ids, DictConstant.HAVE_DEL);
        //删除对应角色资源表中的资源id数据
        roleResourceService.remove(new QueryWrapper<RoleResource>().in("resource_id", ids));
    }

    /**
     * 遍历资源id集合
     * @param id
     * @param ids
     */
    public void getIds(Integer id, List<Integer> ids) {
        //查询父级id
        List<Resource> resourceList = getBaseMapper().selectList(new QueryWrapper<Resource>().eq("parent_id", id));
        if (resourceList != null && resourceList.size() > AuthConstant.ZERO) {
            resourceList.forEach(resource -> {
                ids.add(resource.getId());
                getIds(resource.getId(), ids);
            });
        }
    }
}
