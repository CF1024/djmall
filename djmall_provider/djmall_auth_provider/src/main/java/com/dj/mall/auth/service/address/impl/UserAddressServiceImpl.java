/*
 * 作者：CF
 * 日期：2020-07-28 15:24
 * 项目：djmall
 * 模块：djmall_auth_provider
 * 类名：UserAddressService
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.service.address.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.entity.address.UserAddressEntity;
import com.dj.mall.auth.mapper.address.UserAddressMapper;
import com.dj.mall.auth.service.address.UserAddressService;
import com.dj.mall.model.base.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chengf
 * @date 2020/7/28 15:28
 * 不暴露服务 用户收货地址
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddressEntity> implements UserAddressService {

    /**
     * 展示收货地址
     * @param userId 用户id
     * @return  UserAddressEntity
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public List<UserAddressEntity> findAddressAll(Integer userId) throws Exception, BusinessException {
        return baseMapper.findAddressAllByUserId(userId);
    }
}
