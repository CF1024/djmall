package com.dj.mall.admin.web.product.spu.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.freight.FreightVOResp;
import com.dj.mall.admin.vo.dict.sku.SkuGmVOReq;
import com.dj.mall.admin.vo.dict.sku.SkuGmVOResp;
import com.dj.mall.dict.api.freight.FreightApi;
import com.dj.mall.dict.api.sku.SkuGmApi;
import com.dj.mall.dict.dto.sku.SkuGmDTO;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.product.api.spu.ProductApi;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * 商品展示
     * @return
     */
    @GetMapping("toShow")
    public String toShow() {
        return "product/spu/show";
    }

    /**
     * 新增商品
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("toAddProduct")
    public String toAddProduct(ModelMap model) throws Exception {
        //运费
        model.put("freightList", DozerUtil.mapList(freightApi.findAll(), FreightVOResp.class));
        //商品类型
        PageResult pageResult = skuGmApi.findAll(DozerUtil.map(SkuGmVOReq.class, SkuGmDTO.class));
        model.put("productTypeList", DozerUtil.mapList(pageResult.getList(), SkuGmVOResp.class));
        return "product/spu/add";
    }
}
