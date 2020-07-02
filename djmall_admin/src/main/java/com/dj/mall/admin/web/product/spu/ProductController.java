package com.dj.mall.admin.web.product.spu;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.attr.AttrVOResp;
import com.dj.mall.dict.api.attr.AttrApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.product.api.spu.ProductApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chengf
 * @date 2020/7/2 15:16
 * 商品Controller
 */
@RestController
@RequestMapping("/product/spu/")
public class ProductController {
    /**
     * 商品属性api
     */
    @Reference
    private AttrApi attrApi;
    /**
     * 商品api
     */
    @Reference
    private ProductApi productApi;

    /**
     * 加载通用sku已关联的商品属性
     * @param productType 商品类型
     * @return
     * @throws Exception
     */
    @GetMapping("/{productType}")
    public ResultModel loadSkuGmRelatedAttr(@PathVariable("productType") String productType) throws Exception {
        return new ResultModel().success(DozerUtil.mapList(attrApi.loadSkuGmRelatedAttr(productType), AttrVOResp.class));
    }
}
