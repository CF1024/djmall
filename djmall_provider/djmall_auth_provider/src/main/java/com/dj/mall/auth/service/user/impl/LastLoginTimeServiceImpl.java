/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_provider
 * 类名：LastLoginTimeServiceImpl
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.entity.user.LastLoginTime;
import com.dj.mall.auth.mapper.user.LastLoginTimeMapper;
import com.dj.mall.auth.service.user.LastLoginTimeService;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.statement.Statement;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/5 21:44
 * 时报service实现类 不暴露
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LastLoginTimeServiceImpl extends ServiceImpl<LastLoginTimeMapper, LastLoginTime> implements LastLoginTimeService {
    /**
     * 获取每日用户登录量
     * @param january 一月
     * @return List<Statement>
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public List<Statement> getUserLoginNum(Integer january) throws Exception, BusinessException {
        return baseMapper.getUserLoginNum(january);
    }
}
