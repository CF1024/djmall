/*
 * 作者：CF
 * 日期：2020-07-10 18:08
 * 项目：djmall
 * 模块：djmall_platform
 * 类名：ProductController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.platform.web.product.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.dict.api.sku.SkuGmApi;
import com.dj.mall.dict.dto.sku.SkuGmDTO;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.platform.vo.dict.sku.SkuGmVOReq;
import com.dj.mall.platform.vo.dict.sku.SkuGmVOResp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product/")
public class ProductPageController {
    /**
     * skuGmApi
     */
    @Reference
    private SkuGmApi skuGmApi;

    /**
     * 商城展示
     * @param model ModelMap
     * @return  商城展示页面
     * @throws Exception 异常
     */
    @GetMapping("toShow")
    public String toShow(ModelMap model) throws Exception {
        //商品类型集合
        PageResult pageResult = skuGmApi.findAll(DozerUtil.map(SkuGmVOReq.class, SkuGmDTO.class));
        model.put("productTypeList", DozerUtil.mapList(pageResult.getList(), SkuGmVOResp.class));
        return "product/show";
    }

}
