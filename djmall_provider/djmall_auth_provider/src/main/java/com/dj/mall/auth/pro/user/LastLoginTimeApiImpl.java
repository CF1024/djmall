package com.dj.mall.auth.pro.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.api.user.LastLoginTimeApi;
import com.dj.mall.auth.entity.user.LastLoginTime;
import com.dj.mall.auth.mapper.user.LastLoginTimeMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chengf
 * @date 2020/6/3 20:17
 * 最后登录时间api实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LastLoginTimeApiImpl extends ServiceImpl<LastLoginTimeMapper, LastLoginTime> implements LastLoginTimeApi {
}
