/*
 * 作者：CF
 * 日期：2020-07-07 09:50
 * 项目：djmall
 * 模块：djmall_product_provider
 * 类名：SkuApiImpl
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.product.pro.sku;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.contant.DictConstant;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.product.api.sku.SkuApi;
import com.dj.mall.product.dto.sku.SkuDTO;
import com.dj.mall.product.entity.sku.SkuEntity;
import com.dj.mall.product.mapper.sku.SkuMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chengf
 * @date 2020/7/7 9:51
 * 商品sku
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SkuApiImpl extends ServiceImpl<SkuMapper, SkuEntity> implements SkuApi {

    /**
     * 根据商品Id查找对应已关联的sku数据
     * @param productId 商品Id
     * @return List<SkuDTO>集合
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public List<SkuDTO> findSkuByProductId(Integer productId) throws Exception, BusinessException {
        return DozerUtil.mapList(this.list(new QueryWrapper<SkuEntity>().eq("product_id", productId)), SkuDTO.class);
    }

    /**
     * 根据商品skuId查询商品sku数据
     * @param id 商品skuId
     * @return SkuDTO
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public SkuDTO findSkuBySkuId(Integer id) throws Exception, BusinessException {
        return DozerUtil.map(this.getById(id), SkuDTO.class);
    }

    /**
     * 根据skuId修改商品sku
     * @param skuDTO skuDTO
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public void updateSkuById(SkuDTO skuDTO) throws Exception, BusinessException {
        this.updateById(DozerUtil.map(skuDTO, SkuEntity.class));
    }

    /**
     * 根据ID设为默认 以及将原本是默认的修改为不是默认
     * @param skuId 商品skuId
     * @param productId 商品Id
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public void updateIsDefaultById(Integer skuId, Integer productId) throws Exception, BusinessException {
        //将原本是默认修改为不是默认
        List<SkuEntity> skuEntityList = this.list(new QueryWrapper<SkuEntity>().eq("product_id", productId));
        for (SkuEntity skuEntity : skuEntityList) {
            if (DictConstant.HAVE_DEFAULT.equals(skuEntity.getIsDefault())) {
                skuEntity.setIsDefault(DictConstant.NOT_DEFAULT);
                this.updateById(skuEntity);
            }
        }
        //修改成默认
        this.updateById(this.getById(skuId).toBuilder().isDefault(DictConstant.HAVE_DEFAULT).build());
    }

    /**
     * 根据ID修改上下架状态
     * @param skuId 商品skuId
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public void updateStatusById(Integer skuId) throws Exception, BusinessException {
        SkuEntity skuEntity = this.getById(skuId);
        if (DictConstant.PRODUCT_STATUS_UP.equals(skuEntity.getSkuStatus())) {
            skuEntity.setSkuStatus(DictConstant.PRODUCT_STATUS_DOWN);
            this.updateById(skuEntity);
            return;
        }
        if (DictConstant.PRODUCT_STATUS_DOWN.equals(skuEntity.getSkuStatus())) {
            skuEntity.setSkuStatus(DictConstant.PRODUCT_STATUS_UP);
            this.updateById(skuEntity);
        }
    }

    /**
     * 根据商品skuId查找sku数据
     * @param skuId 商品skuId
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public SkuDTO getSkuBySkuId(Integer skuId) throws Exception, BusinessException {
        return DozerUtil.map(this.getById(skuId), SkuDTO.class);
    }

    /**
     * 根据购物车中的skuId获取对应sku商品集合
     * @param skuIdList skuId集合
     * @return List<ShoppingCartDTO>
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public List<SkuDTO> findSkuBySkuIds(List<Integer> skuIdList) throws Exception, BusinessException {
        return DozerUtil.mapList(baseMapper.findSkuBySkuIds(skuIdList), SkuDTO.class);
    }

    /**
     * 批量修改商品sku库存
     * @param skuList 商品sku集合
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public void updateSkuCountBatchByIds(List<SkuDTO> skuList) throws Exception, BusinessException {
        this.updateBatchById(DozerUtil.mapList(skuList, SkuEntity.class));
    }
}
