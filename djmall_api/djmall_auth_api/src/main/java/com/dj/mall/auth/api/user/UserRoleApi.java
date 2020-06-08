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
