/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_provider
 * 类名：UserMapper
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.mall.auth.bo.user.UserBO;
import com.dj.mall.auth.entity.resource.Resource;
import com.dj.mall.auth.entity.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengf
 * @date 2020/6/3 13:57
 * 用户mapper接口
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 展示查询
     * @param page
     * @param userBO
     * @return
     * @throws Exception
     * @throws DataAccessException
     */
    IPage<UserBO> findAll(@Param("page") Page<?> page, @Param("user") UserBO userBO) throws DataAccessException;

    /**
     * 批量删除
     * @param ids
     * @param isDel
     * @return
     * @throws Exception
     * @throws DataAccessException
     */
    void removeUser(@Param("ids") ArrayList<Integer> ids, @Param("isDel") String isDel) throws DataAccessException;

    /**
     *根据用户id查找用户资源
     * @param userId 用户id
     * @return
     * @throws DataAccessException
     */
    List<Resource> findUserResourceByUserId(Integer userId) throws DataAccessException;
}
