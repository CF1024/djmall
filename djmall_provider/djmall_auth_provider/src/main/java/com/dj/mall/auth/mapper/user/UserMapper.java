package com.dj.mall.auth.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.mall.auth.bo.user.UserBO;
import com.dj.mall.auth.entity.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;

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
    IPage<UserBO> findAll(@Param("page") Page<?> page, @Param("user") UserBO userBO) throws Exception, DataAccessException;

    /**
     * 批量删除
     * @param ids
     * @param isDel
     * @return
     * @throws Exception
     * @throws DataAccessException
     */
    void removeUser(@Param("ids") ArrayList<Integer> ids, @Param("isDel") String isDel) throws Exception, DataAccessException;
}
