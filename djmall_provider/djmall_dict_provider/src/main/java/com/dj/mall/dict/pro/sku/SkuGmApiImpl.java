package com.dj.mall.dict.pro.sku;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.dict.api.sku.SkuGmApi;
import com.dj.mall.dict.bo.sku.SkuGmBO;
import com.dj.mall.dict.dto.sku.SkuGmDTO;
import com.dj.mall.dict.entity.sku.SkuGmEntity;
import com.dj.mall.dict.mapper.sku.SkuGmMapper;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author chengf
 * @date 2020/6/28 18:13
 * 通用sku维护
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SkuGmApiImpl extends ServiceImpl<SkuGmMapper, SkuGmEntity> implements SkuGmApi {
    /**
     * 展示通用sku维护
     *
     * @param skuGmDTO
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public PageResult findAll(SkuGmDTO skuGmDTO) throws Exception, BusinessException {
        IPage<SkuGmBO> iPage = getBaseMapper().findAll(new Page<SkuGmEntity>(skuGmDTO.getPageNo(), skuGmDTO.getPageSize()), DozerUtil.map(skuGmDTO, SkuGmBO.class));
        return new PageResult().toBuilder().pages(iPage.getPages()).list(DozerUtil.mapList(iPage.getRecords(), SkuGmDTO.class)).build();
    }

    /**
     * 根据id查
     *
     * @param productType 商品类型
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public List<SkuGmDTO> findSkuGmByProductType(String productType) throws Exception, BusinessException {
        return DozerUtil.mapList(this.list(new QueryWrapper<SkuGmEntity>().eq("product_type", productType)), SkuGmDTO.class);
    }

    /**
     * 关联属性
     *
     * @param ids
     * @param productType
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void RelatedAttr(Integer[] ids, String productType) throws Exception, BusinessException {
        //先删后增
        this.remove(new QueryWrapper<SkuGmEntity>().eq("product_type", productType));
        List<SkuGmEntity> skuGmEntityList = new LinkedList<>();
        for (Integer id : ids) {
            SkuGmEntity skuGmEntity = new SkuGmEntity().toBuilder().productType(productType).attrId(id).build();
            skuGmEntityList.add(skuGmEntity);
        }
        this.saveBatch(skuGmEntityList);
    }

    /**
     * 去重
     *
     * @param productType
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public Boolean deDuplicate(String productType) throws Exception, BusinessException {
        return this.getOne(new QueryWrapper<SkuGmEntity>().eq("product_type", productType)) == null;
    }

    /**
     * 新增
     *
     * @param skuGmDTO
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void addSkuGm(SkuGmDTO skuGmDTO) throws Exception, BusinessException {
        this.save(DozerUtil.map(skuGmDTO, SkuGmEntity.class));
    }
}
