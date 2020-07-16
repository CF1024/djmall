/*
 * 作者：CF
 * 日期：2020-07-10 18:08
 * 项目：djmall
 * 模块：djmall_platform
 * 类名：ProductController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.platform.web.product;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.platform.vo.product.sku.SkuVOResp;
import com.dj.mall.platform.vo.product.spu.ProductVOReq;
import com.dj.mall.platform.vo.product.spu.ProductVOResp;
import com.dj.mall.product.api.sku.SkuApi;
import com.dj.mall.product.api.spu.ProductApi;
import com.dj.mall.product.dto.spu.ProductDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/")
public class ProductController {
    /**
     * 商品api
     */
    @Reference
    private ProductApi productApi;
    /**
     * 商品skuApi
     */
    @Reference
    private SkuApi skuApi;

    /**
     * 商城展示
     * @param productVOReq  productVOReq
     * @return ResultModel
     * @throws Exception 异常信息
     */
    @GetMapping
    public ResultModel<Object> show(ProductVOReq productVOReq) throws Exception {
        PageResult pageResult = productApi.findList(DozerUtil.map(productVOReq, ProductDTO.class));
        return new ResultModel<>().success(pageResult.toBuilder().list(DozerUtil.mapList(pageResult.getList(), ProductVOResp.class)).build());
    }

    /**
     *
     * 根据商品skuId查找sku数据
     * @return SkuVOResp
     * @throws Exception 异常
     */
    @GetMapping("getSkuBySkuId")
    public ResultModel<Object> getSkuBySkuId(Integer skuId) throws Exception {
        return new ResultModel<>().success(DozerUtil.map(skuApi.getSkuBySkuId(skuId), SkuVOResp.class));
    }
}
