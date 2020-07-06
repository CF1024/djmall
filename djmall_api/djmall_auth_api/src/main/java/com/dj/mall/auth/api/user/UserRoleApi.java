/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_api
 * 类名：UserRoleApi
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.api.user;

import com.dj.mall.auth.dto.user.UserRoleDTO;
import com.dj.mall.model.base.BusinessException;

/**
 * @author chengf
 * @date 2020/6/3 23:02
 * 用户角色api接口
 */
public interface UserRoleApi {
    /**
     * 根据用户id查
     * @param userId
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    UserRoleDTO findUserRoleById(Integer userId) throws Exception, BusinessException;

    /**
     * 修改用户的角色 授权
     * @param userRoleDTO
     * @throws Exception
     * @throws BusinessException
     */
    void updateUserRole(UserRoleDTO userRoleDTO) throws Exception, BusinessException;
}
