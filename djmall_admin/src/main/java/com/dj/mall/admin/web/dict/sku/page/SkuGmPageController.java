/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：SkuGmPageController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.web.dict.sku.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.sku.SkuGmVOResp;
import com.dj.mall.dict.api.sku.SkuGmApi;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/28 18:13
 * 通用sku维护
 */
@Controller
@RequestMapping("/dict/skuGm/")
public class SkuGmPageController {
    /**
     * 通用sku维护Api
     */
    @Reference
    private SkuGmApi skuGmApi;

    /**
     * 去展示通用sku维护
     * @return
     */
    @GetMapping("toShow")
    @RequiresPermissions(value = PermissionsCode.SKU_GM_MANAGE)
    public String toShow() {
        return "dict/sku/show";
    }

    /**
     * 去关联属性
     * @param productType 商品类型
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("toRelatedAttr/{productType}")
    @RequiresPermissions(value = PermissionsCode.RELATED_ATTR_BTN)
    public String toRelatedAttr(@PathVariable("productType") String productType, ModelMap model) throws Exception {
        List<SkuGmVOResp> skuGmList = DozerUtil.mapList(skuGmApi.findSkuGmByProductType(productType), SkuGmVOResp.class);
        String attrIds = "";
        for (SkuGmVOResp skuGm : skuGmList) {
            attrIds += skuGm.getAttrId() + ",";
        }
        System.out.println(attrIds);
        model.put("productType", productType);
        model.put("attrIds", attrIds);
        return "dict/sku/related";
    }

}
