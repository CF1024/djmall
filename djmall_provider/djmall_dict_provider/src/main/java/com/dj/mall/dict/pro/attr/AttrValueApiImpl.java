/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_dict_provider
 * 类名：AttrValueApiImpl
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.dict.pro.attr;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.dict.api.attr.AttrValueApi;
import com.dj.mall.dict.dto.attr.AttrDTO;
import com.dj.mall.dict.dto.attr.AttrValueDTO;
import com.dj.mall.dict.entity.attr.AttrValueEntity;
import com.dj.mall.dict.mapper.attr.AttrValueMapper;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author chengf
 * @date 2020/6/28 14:56
 * 商品属性值
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AttrValueApiImpl extends ServiceImpl<AttrValueMapper, AttrValueEntity> implements AttrValueApi {
    /**
     * 展示商品属性值
     *
     * @param attrValueDTO
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public PageResult findAll(AttrValueDTO attrValueDTO) throws Exception, BusinessException {
        IPage<AttrValueEntity> iPage = this.page(new Page<>(attrValueDTO.getPageNo(), attrValueDTO.getPageSize()),
                new QueryWrapper<AttrValueEntity>().eq("attr_id", attrValueDTO.getAttrId()));
        return new PageResult().toBuilder().pages(iPage.getPages()).list(DozerUtil.mapList(iPage.getRecords(), AttrValueDTO.class)).build();
    }

    /**
     * 去重
     *
     * @param attrValue
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public Boolean deDuplicate(String attrValue) throws Exception, BusinessException {
        return this.getOne(new QueryWrapper<AttrValueEntity>().eq("attr_value", attrValue)) ==  null;
    }

    /**
     * 新增
     *
     * @param attrValueDTO
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void addAttrValue(AttrValueDTO attrValueDTO) throws Exception, BusinessException {
        this.save(DozerUtil.map(attrValueDTO, AttrValueEntity.class));
    }

    /**
     * 删除
     *
     * @param attrValueId
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void deleteAttrValue(Integer attrValueId) throws Exception, BusinessException {
        this.removeById(attrValueId);
    }
}
