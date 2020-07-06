/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_dict_api
 * 类名：FreightApi
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.dict.api.freight;

import com.dj.mall.dict.dto.freight.FreightDTO;
import com.dj.mall.model.base.BusinessException;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/16 14:20
 * 运费
 */
public interface FreightApi {
    /**
     * 展示
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    List<FreightDTO> findAll() throws Exception, BusinessException;

    /**
     * 新增
     * @param freightDTO
     * @throws Exception
     * @throws BusinessException
     */
    void addFreight(FreightDTO freightDTO) throws Exception, BusinessException;

    /**
     * 根据id查运费数据
     * @param freightId
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    FreightDTO findFreightById(Integer freightId) throws Exception, BusinessException;

    /**
     * 修改
     * @param freightDTO
     * @throws Exception
     * @throws BusinessException
     */
    void updateFreight(FreightDTO freightDTO) throws Exception, BusinessException;
}
