/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_provider
 * 类名：UserRoleApiImpl
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.pro.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.api.user.UserRoleApi;
import com.dj.mall.auth.dto.user.UserRoleDTO;
import com.dj.mall.auth.entity.user.UserRole;
import com.dj.mall.auth.mapper.user.UserRoleMapper;
import com.dj.mall.auth.service.user.UserRoleService;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chengf
 * @date 2020/6/3 23:05
 * 用户角色api实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserRoleApiImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleApi {
    @Autowired
    private UserRoleService userRoleService;
    /**
     * 根据用户id查
     * @param userId
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public UserRoleDTO findUserRoleById(Integer userId) throws Exception, BusinessException {
        return DozerUtil.map(getBaseMapper().selectOne(new QueryWrapper<UserRole>().eq("user_id", userId)), UserRoleDTO.class);
    }

    /**
     * 修改用户的角色 授权
     * @param userRoleDTO
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void updateUserRole(UserRoleDTO userRoleDTO) throws Exception, BusinessException {
        //根据用户id查用户角色的数据
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<UserRole>().eq("user_id", userRoleDTO.getUserId());
        //将角色id更新为前台传过来的角色id
        UserRole userRole = getBaseMapper().selectOne(queryWrapper).toBuilder().roleId(userRoleDTO.getRoleId()).build();
        userRoleService.update(userRole, queryWrapper);
    }
}
