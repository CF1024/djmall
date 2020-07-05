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
     *
     * @param productDTO 商品dto
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public PageResult findAll(ProductDTO productDTO) throws Exception, BusinessException {
        Page<ProductEntity> page = new Page<>(productDTO.getPageNo(), productDTO.getPageSize());
        IPage<ProductBO>  iPage = getBaseMapper().findAll(page, DozerUtil.map(productDTO, ProductBO.class));
        return new PageResult().toBuilder().pages(iPage.getPages()).list(DozerUtil.mapList(iPage.getRecords(), ProductDTO.class)).build();
    }

    /**
     * 去重
     * @param productDTO
     * @return
     * @throws Exception
     * @throws BusinessException
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
     *
     * @param productDTO
     * @param file
     * @throws Exception
     * @throws BusinessException
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
        skuEntityList.get(AuthConstant.ZERO).setIsDefault(DictConstant.HAVE_DEFAULT);
        skuService.saveBatch(skuEntityList);
        //七牛云上传
        InputStream inputStream = new ByteArrayInputStream(file);
        QiniuUtils.uploadByInputStream(inputStream, productEntity.getProductImg());
    }

}
