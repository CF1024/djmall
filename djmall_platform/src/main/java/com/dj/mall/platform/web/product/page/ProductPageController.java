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
import com.dj.mall.dict.api.freight.FreightApi;
import com.dj.mall.dict.api.sku.SkuGmApi;
import com.dj.mall.dict.dto.freight.FreightDTO;
import com.dj.mall.dict.dto.sku.SkuGmDTO;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.platform.vo.dict.freight.FreightVOReq;
import com.dj.mall.platform.vo.dict.freight.FreightVOResp;
import com.dj.mall.platform.vo.dict.sku.SkuGmVOReq;
import com.dj.mall.platform.vo.dict.sku.SkuGmVOResp;
import com.dj.mall.platform.vo.product.sku.SkuVOResp;
import com.dj.mall.platform.vo.product.spu.ProductVOResp;
import com.dj.mall.product.api.sku.SkuApi;
import com.dj.mall.product.api.spu.ProductApi;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
     * skuApi
     */
    @Reference
    private SkuApi skuApi;
    /**
     * productApi
     */
    @Reference
    private ProductApi productApi;
    /**
     * freightApi
     */
    @Reference
    private FreightApi freightApi;

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

    /**
     * 到产品详细信息
     * @param id 商品ID
     * @param model ModelMap
     * @return 到产品详细信息页面
     * @throws Exception 异常信息
     */
    @GetMapping("toProductDetails")
    public String toProductDetails(Integer id, ModelMap model) throws Exception {
        //商品
        model.put("product", DozerUtil.map(productApi.findProductById(id), ProductVOResp.class));
        //商品sku集合
        model.put("sku", DozerUtil.mapList(skuApi.findSkuByProductId(id), SkuVOResp.class));
        //运费
        model.put("freight", DozerUtil.mapList(freightApi.findAll(), FreightVOResp.class));
        return "product/detail";
    }
}
