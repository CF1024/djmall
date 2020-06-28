package com.dj.mall.admin.web.dict.sku;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.sku.SkuGmVOReq;
import com.dj.mall.admin.vo.dict.sku.SkuGmVOResp;
import com.dj.mall.dict.api.sku.SkuGmApi;
import com.dj.mall.dict.dto.sku.SkuGmDTO;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chengf
 * @date 2020/6/28 18:13
 * 通用sku维护
 */
@RestController
@RequestMapping("/dict/skuGm/")
public class SkuGmController {
    /**
     * 通用sku维护Api
     */
    @Reference
    private SkuGmApi skuGmApi;

    /**
     * 展示通用sku维护
     * @param skuGmVOReq
     * @return
     * @throws Exception
     */
    @GetMapping
    @RequiresPermissions(value = PermissionsCode.SKU_GM_MANAGE)
    public ResultModel show(SkuGmVOReq skuGmVOReq) throws Exception {
        PageResult pageResult = skuGmApi.findAll(DozerUtil.map(skuGmVOReq, SkuGmDTO.class));
        return new ResultModel().success(pageResult.toBuilder().list(DozerUtil.mapList(pageResult.getList(), SkuGmVOResp.class)).build());
    }

    /**
     * 关联属性
     * @param ids
     * @param productType
     * @return
     * @throws Exception
     */
    @PostMapping
    @RequiresPermissions(value = PermissionsCode.RELATED_ATTR_BTN)
    public ResultModel<Object> RelatedAttr(Integer[] ids, String productType) throws Exception {
        skuGmApi.RelatedAttr(ids, productType);
        return new ResultModel<>().success("关联成功");
    }

    /**
     * 去重
     * @param productType
     * @return
     * @throws Exception
     */
    @GetMapping("deDuplicate")
    public Boolean deDuplicate(String productType) throws Exception {
        return skuGmApi.deDuplicate(productType);
    }

    /**
     * 新增
     * @param skuGmVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("addSkuGm")
    @RequiresPermissions(value = PermissionsCode.SKU_GM_ADD_BTN)
    public ResultModel<Object> addSkuGm(SkuGmVOReq skuGmVOReq) throws Exception {
        skuGmApi.addSkuGm(DozerUtil.map(skuGmVOReq, SkuGmDTO.class));
        return new ResultModel<>().success("关联成功");
    }
}
