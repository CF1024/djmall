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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author chengf
 * @date 2020/6/5 21:44
 * 时报service实现类 不暴露
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LastLoginTimeServiceImpl extends ServiceImpl<LastLoginTimeMapper, LastLoginTime> implements LastLoginTimeService {
}
