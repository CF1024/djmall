package com.dj.mall.auth.pro.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.api.user.UserRoleApi;
import com.dj.mall.auth.entity.user.UserRole;
import com.dj.mall.auth.mapper.user.UserRoleMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chengf
 * @date 2020/6/3 23:05
 * 用户角色api实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserRoleApiImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleApi {
}
