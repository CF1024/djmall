/*
 * 作者：CF
 * 日期：2020-07-07 10:41
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：SkuController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.web.product.sku;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.product.sku.SkuVOReq;
import com.dj.mall.admin.vo.product.sku.SkuVOResp;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.product.api.sku.SkuApi;
import com.dj.mall.product.dto.sku.SkuDTO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;


/**
 * @author chengf
 * @date 2020/7/7 10:42
 * 商品Sku
 */
@RestController
@RequestMapping("/product/sku/")
public class SkuController {
    /**
     * sku的api
     */
    @Reference
    private SkuApi skuApi;

    /**
     * 根据商品id展示商品sku已关联数据
     * @param productId  商品id
     * @return SkuVOResp数据集合
     * @throws Exception 异常
     */
    @GetMapping
    @RequiresPermissions(value = PermissionsCode.PRODUCT_SKU_SHOW_BTN)
    public ResultModel<Object> show(Integer productId) throws Exception {
        return new ResultModel<>().success(DozerUtil.mapList(skuApi.findSkuByProductId(productId), SkuVOResp.class));
    }

    /**
     * 修改 商品sku
     * @param skuVOReq skuVOReq
     * @return SkuVOResp数据集合
     * @throws Exception 异常
     */
    @PutMapping
    @RequiresPermissions(value = PermissionsCode.PRODUCT_SKU_EDIT_BTN)
    public ResultModel<Object> updateSku(SkuVOReq skuVOReq) throws Exception {
        skuApi.updateSkuById(DozerUtil.map(skuVOReq, SkuDTO.class));
        return new ResultModel<>().success("修改成功");
    }

    /**
     * 根据ID设为默认 以及将原本是默认的修改为不是默认
     * @param skuId 商品skuId
     * @param productId 商品Id
     * @return 修改成功
     * @throws Exception 异常
     */
    @PostMapping
    @RequiresPermissions(value = PermissionsCode.PRODUCT_SKU_SET_AS_DEFAULT)
    public ResultModel<Object> setAsDefault(Integer skuId, Integer productId) throws Exception {
        skuApi.updateIsDefaultById(skuId, productId);
        return new ResultModel<>().success("修改成功");
    }

    /**
     * 根据ID修改上下架状态
     * @param skuId 商品skuId
     * @throws Exception 异常
     */
    @PostMapping("updateStatus")
    @RequiresPermissions(value = PermissionsCode.PRODUCT_SKU_UPDATE_STATUS)
    public ResultModel<Object> updateStatus(Integer skuId) throws Exception {
        skuApi.updateStatusById(skuId);
        return new ResultModel<>().success("操作成功");
    }

}
