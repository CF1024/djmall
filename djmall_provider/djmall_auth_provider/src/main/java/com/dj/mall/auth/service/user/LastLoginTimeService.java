/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_provider
 * 类名：LastLoginTimeService
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.mall.auth.entity.user.LastLoginTime;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.statement.Statement;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/5 21:44
 * 时报service接口 不暴露
 */
public interface LastLoginTimeService extends IService<LastLoginTime> {

    /**
     * 获取每日用户登录量
     * @param january 一月
     * @return List<Statement>
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    List<Statement> getUserLoginNum(Integer january) throws Exception, BusinessException;
}
