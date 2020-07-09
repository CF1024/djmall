/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_product_provider
 * 类名：ProductApiImpl
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.product.pro.spu;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.contant.AuthConstant;
import com.dj.mall.model.contant.DictConstant;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.QiniuUtils;
import com.dj.mall.product.api.spu.ProductApi;
import com.dj.mall.product.bo.spu.ProductBO;
import com.dj.mall.product.dto.spu.ProductDTO;
import com.dj.mall.product.entity.sku.SkuEntity;
import com.dj.mall.product.entity.spu.ProductEntity;
import com.dj.mall.product.mapper.spu.ProductMapper;
import com.dj.mall.product.service.sku.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author chengf
 * @date 2020/7/2 15:16
 * 商品ApiImpl
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductApiImpl extends ServiceImpl<ProductMapper, ProductEntity> implements ProductApi {
    /**
     * 不暴露服务 skuService
     */
    @Autowired
    private SkuService skuService;

    /**
     * 商品展示
     * @param productDTO 商品dto
     * @return PageResult
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public PageResult findAll(ProductDTO productDTO) throws Exception, BusinessException {
        Page<ProductEntity> page = new Page<>(productDTO.getPageNo(), productDTO.getPageSize());
        IPage<ProductBO>  iPage = getBaseMapper().findAll(page, DozerUtil.map(productDTO, ProductBO.class));
        return new PageResult().toBuilder().pages(iPage.getPages()).list(DozerUtil.mapList(iPage.getRecords(), ProductDTO.class)).build();
    }

    /**
     * 去重
     * @param productDTO 商品Dto
     * @return Boolean
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public Boolean deDuplicate(ProductDTO productDTO) throws Exception, BusinessException {
        QueryWrapper<ProductEntity> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(productDTO.getProductId())) {
            queryWrapper.ne("id", productDTO.getProductId());
        }
        if (!StringUtils.isEmpty(productDTO.getProductName())) {
            queryWrapper.eq("product_name", productDTO.getProductName());
        }
        return this.getOne(queryWrapper) == null;
    }

    /**
     * 商品新增
     * @param productDTO 商品Dto
     * @param file byte流文件
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public void addProduct(ProductDTO productDTO, byte[] file) throws Exception, BusinessException {
        ProductEntity productEntity = DozerUtil.map(productDTO, ProductEntity.class);
        this.save(productEntity);
        List<SkuEntity> skuEntityList = DozerUtil.mapList(productDTO.getProductSkuList(), SkuEntity.class);
        skuEntityList.forEach(skuEntity -> {
            skuEntity.setProductId(productEntity.getId());
            skuEntity.setSkuStatus(DictConstant.PRODUCT_STATUS_UP);
            skuEntity.setIsDefault(DictConstant.NOT_DEFAULT);
        });
        //集合下标为0的sku数据的是否默认：设为默认
        skuEntityList.get(AuthConstant.ZERO).setIsDefault(DictConstant.HAVE_DEFAULT);
        skuService.saveBatch(skuEntityList);
        //七牛云上传
        InputStream inputStream = new ByteArrayInputStream(file);
        QiniuUtils.uploadByInputStream(inputStream, productEntity.getProductImg());
    }

    /**
     * 根据id修改商品和商品sku上下架状态
     * @param id 商品ID
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public void updateProductStatusById(Integer id) throws Exception, BusinessException {
        //根据id获取商品信息 和 商品sku的信息
        ProductEntity productEntity = this.getById(id);
        List<SkuEntity> skuEntityList = skuService.list(new QueryWrapper<SkuEntity>().eq("product_id", id));
        //商品修改 商品sku批量修改 上架 && 下架
        if (DictConstant.PRODUCT_STATUS_UP.equals(productEntity.getProductStatus())) {
            productEntity.setProductStatus(DictConstant.PRODUCT_STATUS_DOWN);
            skuEntityList.forEach(skuEntity -> skuEntity.setSkuStatus(DictConstant.PRODUCT_STATUS_DOWN));
            this.updateById(productEntity);
            skuService.updateBatchById(skuEntityList);
            return;
        }
        if (DictConstant.PRODUCT_STATUS_DOWN.equals(productEntity.getProductStatus())) {
            productEntity.setProductStatus(DictConstant.PRODUCT_STATUS_UP);
            skuEntityList.forEach(skuEntity -> skuEntity.setSkuStatus(DictConstant.PRODUCT_STATUS_UP));
            this.updateById(productEntity);
            skuService.updateBatchById(skuEntityList);
        }


    }

    /**
     * 根据id查商品信息
     * @param productId 商品ID
     * @return ProductDTO
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public ProductDTO findProductById(Integer productId) throws Exception, BusinessException {
        return DozerUtil.map(this.getById(productId), ProductDTO.class);
    }

    /**
     * 根据id修改商品
     * @param productDTO productDTO
     * @param file 图片文件流
     * @throws Exception 异常
     * @throws BusinessException 业务处理异常
     */
    @Override
    public void updateProductById(ProductDTO productDTO, byte[] file) throws Exception, BusinessException {
        this.updateById(DozerUtil.map(productDTO, ProductEntity.class));
        //七牛云删除之前的以及上传新的
        if (!StringUtils.isEmpty(file)) {
            QiniuUtils.delFile(productDTO.getRemoveImg());
            InputStream inputStream = new ByteArrayInputStream(file);
            QiniuUtils.uploadByInputStream(inputStream, productDTO.getProductImg());
        }
    }

}
