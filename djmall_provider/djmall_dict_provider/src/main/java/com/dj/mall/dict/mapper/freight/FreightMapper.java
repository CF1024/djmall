/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_dict_provider
 * 类名：FreightMapper
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.dict.mapper.freight;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.dict.bo.freight.FreightBO;
import com.dj.mall.dict.entity.freight.FreightEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/16 14:20
 * 运费
 */
public interface FreightMapper extends BaseMapper<FreightEntity> {
    /**
     * 查询运费全部
     * @return
     * @throws DataAccessException
     */
    List<FreightBO> findAll() throws DataAccessException;
}
