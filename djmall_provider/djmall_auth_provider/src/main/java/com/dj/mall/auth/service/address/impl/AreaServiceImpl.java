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
import com.dj.mall.auth.entity.address.AreaEntity;
import com.dj.mall.auth.mapper.address.AreaMapper;
import com.dj.mall.auth.service.address.AreaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chengf
 * @date 2020/7/28 15:28
 * 不暴露服务 省市县
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AreaServiceImpl extends ServiceImpl<AreaMapper, AreaEntity> implements AreaService {
}
