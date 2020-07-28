/*
 * 作者：CF
 * 日期：2020-07-28 15:24
 * 项目：djmall
 * 模块：djmall_auth_provider
 * 类名：UserAddressService
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.mapper.address;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.mall.auth.entity.address.UserAddressEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author chengf
 * @date 2020/7/28 15:28
 * 用户收货地址
 */
public interface UserAddressMapper extends BaseMapper<UserAddressEntity> {
    /**
     * 购物车展示
     * @param userId 用户id
     * @return 收货地址实体类集合
     * @throws DataAccessException 异常
     */
    List<UserAddressEntity> findAddressAllByUserId(Integer userId) throws DataAccessException;
}
