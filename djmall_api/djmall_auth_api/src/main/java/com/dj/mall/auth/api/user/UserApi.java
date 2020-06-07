package com.dj.mall.auth.api.user;

import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.PageResult;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/3 14:59
 * 用户api接口
 */
public interface UserApi {
    /**
     * 用户登录
     * @param userName
     * @param userPwd
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    UserDTO findUserByNameAndPwd(String userName, String userPwd) throws Exception, BusinessException;

    /**
     * 去重
     * @param userDTO
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    Boolean deDuplicate(UserDTO userDTO) throws Exception, BusinessException;

    /**
     * 新增用户
     * @param userDTO
     * @throws Exception
     * @throws BusinessException
     */
    void addUser(UserDTO userDTO) throws Exception, BusinessException;
    /**
     * 修改激活状态 邮箱激活
     * @param id
     * @throws Exception
     * @throws BusinessException
     */
    void updateUserStatus(Integer id) throws Exception, BusinessException;

    /**
     * 展示用户 分页 模糊查 查询
     * @param userDTO
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    PageResult findAll(UserDTO userDTO) throws Exception, BusinessException;
}