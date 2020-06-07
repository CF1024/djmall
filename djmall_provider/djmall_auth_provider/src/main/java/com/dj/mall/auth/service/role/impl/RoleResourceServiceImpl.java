package com.dj.mall.auth.service.role.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.entity.role.RoleResource;
import com.dj.mall.auth.mapper.role.RoleResourceMapper;
import com.dj.mall.auth.service.role.RoleResourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author chengf
 * @date 2020/6/5 13:43
 * 关联资源service实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements RoleResourceService {
}
