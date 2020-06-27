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
}
