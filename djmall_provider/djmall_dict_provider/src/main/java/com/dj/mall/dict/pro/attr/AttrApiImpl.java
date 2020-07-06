/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_dict_provider
 * 类名：AttrApiImpl
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.dict.pro.attr;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.dict.api.attr.AttrApi;
import com.dj.mall.dict.dto.attr.AttrDTO;
import com.dj.mall.dict.entity.attr.AttrEntity;
import com.dj.mall.dict.mapper.attr.AttrMapper;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/27 20:56
 * 商品属性
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AttrApiImpl extends ServiceImpl<AttrMapper, AttrEntity> implements AttrApi {
    /**
     * 展示商品属性
     *
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public List<AttrDTO> findAll() throws Exception, BusinessException {
        return DozerUtil.mapList(getBaseMapper().findAll(), AttrDTO.class);
    }

    /**
     * 去重
     *
     * @param attrId
     * @param attrName
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public Boolean deDuplicate(Integer attrId, String attrName) throws Exception, BusinessException {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(attrId)) {
            queryWrapper.ne("id", attrId);
        }
        if (!StringUtils.isEmpty(attrName)) {
            queryWrapper.eq("attr_name", attrName);
        }
        return this.getOne(queryWrapper) == null;
    }

    /**
     * 新增
     *
     * @param attrDTO
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void addAttr(AttrDTO attrDTO) throws Exception, BusinessException {
        this.save(DozerUtil.map(attrDTO, AttrEntity.class));
    }

    /**
     * 根据id查 去关联属性值
     *
     * @param attrId
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public AttrDTO findAttrById(Integer attrId) throws Exception, BusinessException {
        return DozerUtil.map(this.getById(attrId), AttrDTO.class);
    }

    /**
     * 加载通用sku已关联的商品属性根据商品类型查
     * @param productType 商品类型
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public List<AttrDTO> loadSkuGmRelatedAttr(String productType) throws Exception, BusinessException {
        return DozerUtil.mapList(getBaseMapper().loadSkuGmRelatedAttrByProductType(productType), AttrDTO.class);
    }
}
