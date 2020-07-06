/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_dict_provider
 * 类名：FreightApiImpl
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.dict.pro.freight;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.dict.api.freight.FreightApi;
import com.dj.mall.dict.dto.freight.FreightDTO;
import com.dj.mall.dict.entity.freight.FreightEntity;
import com.dj.mall.dict.mapper.freight.FreightMapper;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/16 14:20
 * 运费
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FreightApiImpl extends ServiceImpl<FreightMapper, FreightEntity> implements FreightApi {
    /**
     * 展示
     *
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public List<FreightDTO> findAll() throws Exception, BusinessException {
        return DozerUtil.mapList(getBaseMapper().findAll(), FreightDTO.class);
    }

    /**
     * 新增
     * @param freightDTO
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void addFreight(FreightDTO freightDTO) throws Exception, BusinessException {
        getBaseMapper().insert(DozerUtil.map(freightDTO, FreightEntity.class));
    }

    /**
     * 根据id查运费数据
     * @param freightId
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public FreightDTO findFreightById(Integer freightId) throws Exception, BusinessException {
        return DozerUtil.map(getBaseMapper().selectById(freightId), FreightDTO.class);
    }

    /**
     * 修改
     * @param freightDTO
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void updateFreight(FreightDTO freightDTO) throws Exception, BusinessException {
        getBaseMapper().updateById(DozerUtil.map(freightDTO, FreightEntity.class));
    }
}
