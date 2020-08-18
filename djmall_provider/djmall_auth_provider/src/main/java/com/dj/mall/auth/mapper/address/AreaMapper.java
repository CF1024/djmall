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
import com.dj.mall.auth.bo.address.AreaBO;
import com.dj.mall.auth.entity.address.AreaEntity;
import org.springframework.dao.DataAccessException;

/**
 * @author chengf
 * @date 2020/7/28 15:28
 * 省市县
 */
public interface AreaMapper extends BaseMapper<AreaEntity> {
}
