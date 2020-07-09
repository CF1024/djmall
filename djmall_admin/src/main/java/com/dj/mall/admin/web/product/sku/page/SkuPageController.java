/*
 * 作者：CF
 * 日期：2020-07-07 10:41
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：SkuController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.web.product.sku.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.product.sku.SkuVOResp;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.product.api.sku.SkuApi;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @author chengf
 * @date 2020/7/7 10:42
 * 商品Sku
 */
@Controller
@RequestMapping("/product/sku/")
public class SkuPageController {
    /**
     * sku的api
     */
    @Reference
    private SkuApi skuApi;

    /**
     * 去修改库存
     * @param id 商品sku的id
     * @param model ModelMap
     * @return SkuVOResp数据
     * @throws Exception 异常
     */
    @GetMapping("toUpdateCount/{id}")
    @RequiresPermissions(value = PermissionsCode.PRODUCT_SKU_UPDATE_COUNT_BTN)
    public String toUpdateCount(@PathVariable("id") Integer id, ModelMap model) throws Exception {
        model.put("sku", DozerUtil.map(skuApi.findSkuBySkuId(id), SkuVOResp.class));
        return "product/sku/update_count";
    }

    /**
     * 去编辑
     * @param id 商品sku的id
     * @param model ModelMap
     * @return SkuVOResp数据
     * @throws Exception 异常
     */
    @GetMapping("toEdit/{id}")
    @RequiresPermissions(value = PermissionsCode.PRODUCT_SKU_EDIT_BTN)
    public String toEdit(@PathVariable("id") Integer id, ModelMap model) throws Exception {
        model.put("sku", DozerUtil.map(skuApi.findSkuBySkuId(id), SkuVOResp.class));
        return "product/sku/edit";
    }
}
