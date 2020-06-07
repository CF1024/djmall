package com.dj.mall.auth.pro.role;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.api.role.RoleResourceApi;
import com.dj.mall.auth.entity.role.RoleResource;
import com.dj.mall.auth.mapper.role.RoleResourceMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chengf
 * @date 2020/6/3 23:05
 * 角色资源api实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleResourceApiImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements RoleResourceApi {
}
