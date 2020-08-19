/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_provider
 * 类名：LastLoginTimeMapper
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.auth.entity.user.LastLoginTime;
import com.dj.mall.model.statement.Statement;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/3 20:16
 * 最后登录时间mapper接口
 */
public interface LastLoginTimeMapper extends BaseMapper<LastLoginTime> {
    /**
     * 获取每日用户登录量
     * @param january 一月
     * @return List<Statement>
     * @throws DataAccessException
     */
    List<Statement> getUserLoginNum(Integer january) throws DataAccessException;
}
