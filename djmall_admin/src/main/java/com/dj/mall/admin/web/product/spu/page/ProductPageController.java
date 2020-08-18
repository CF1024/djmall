/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：ProductPageController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.web.product.spu.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.freight.FreightVOResp;
import com.dj.mall.admin.vo.dict.sku.SkuGmVOReq;
import com.dj.mall.admin.vo.dict.sku.SkuGmVOResp;
import com.dj.mall.admin.vo.product.sku.SkuVOResp;
import com.dj.mall.admin.vo.product.spu.ProductVOResp;
import com.dj.mall.dict.api.freight.FreightApi;
import com.dj.mall.dict.api.sku.SkuGmApi;
import com.dj.mall.dict.dto.sku.SkuGmDTO;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.product.api.sku.SkuApi;
import com.dj.mall.product.api.spu.ProductApi;
import com.dj.mall.product.dto.sku.SkuDTO;
import com.dj.mall.product.dto.spu.ProductDTO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author chengf
 * @date 2020/7/2 15:16
 * 商品PageController
 */
@Controller
@RequestMapping("/product/spu/")
public class ProductPageController {
    /**
     * 商品api
     */
    @Reference
    private ProductApi productApi;
    /**
     * 运费api
     */
    @Reference
    private FreightApi freightApi;
    /**
     * skuGm的api
     */
    @Reference
    private SkuGmApi skuGmApi;
    /**
     * sku的api
     */
    @Reference
    private SkuApi skuApi;

    /**
     * 商品展示
     * @param model ModelMap
     * @return show展示页面
     * @throws Exception 异常
     */
    @GetMapping("toShow")
    @RequiresPermissions(value = PermissionsCode.PRODUCT_MANAGE)
    public String toShow(ModelMap model) throws Exception {
        //商品类型集合
        model.put("productTypeList", DozerUtil.mapList( skuGmApi.findSkuGm(), SkuGmVOResp.class));
        return "product/spu/show";
    }

    /**
     * 新增商品
     * @param model ModelMap
     * @return add新增页面
     * @throws Exception 异常
     */
    @GetMapping("toAddProduct")
    @RequiresPermissions(value = PermissionsCode.PRODUCT_ADD_BTN)
    public String toAddProduct(ModelMap model) throws Exception {
        //运费集合
        model.put("freightList", DozerUtil.mapList(freightApi.findAll(), FreightVOResp.class));
        //商品类型集合
        model.put("productTypeList", DozerUtil.mapList( skuGmApi.findSkuGm(), SkuGmVOResp.class));
        return "product/spu/add";
    }

    /**
     * 根据商品ID查找商品数据
     * @param productId 商品ID
     * @param model ModelMap
     * @return update修改页面
     * @throws Exception 异常
     */
    @GetMapping("toUpdateProduct/{productId}")
    @RequiresPermissions(value = PermissionsCode.PRODUCT_UPDATE_BTN)
    public String toUpdateProduct(@PathVariable("productId") Integer productId, ModelMap model) throws Exception {
        //根据商品ID获取 商品信息 商品sku集合信息
        model.put("product", DozerUtil.map(productApi.findProductById(productId), ProductVOResp.class));
        model.put("freightList", DozerUtil.mapList(freightApi.findAll(), FreightVOResp.class));
        return "product/spu/update";
    }

    /**
     * 去查看评论
     * @param productId 商品id
     * @param model  ModelMap
     * @return 查看评论前台
     * @throws Exception 异常
     */
    @GetMapping("toViewComments/{productId}")
    @RequiresPermissions(value = PermissionsCode.VIEW_COMMENTS_BTN)
    public String toViewComments(@PathVariable("productId") Integer productId, ModelMap model) throws Exception {
        model.put("productId", productId);
        //商品好评率
        model.put("goodRate", productApi.findGoodRateByProductId(productId));
        return "product/spu/comment";
    }
}
