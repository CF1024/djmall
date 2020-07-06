/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：AttrPageController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.web.dict.attr.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.attr.AttrVOResp;
import com.dj.mall.dict.api.attr.AttrApi;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chengf
 * @date 2020/6/27 20:56
 * 商品属性
 */
@Controller
@RequestMapping("/dict/attr/")
public class AttrPageController {
    @Reference
    private AttrApi attrApi;
    /**
     * 去展示属性
     * @return
     */
    @GetMapping("toShow")
    @RequiresPermissions(value = PermissionsCode.ATTR_MANAGE)
    public String toShow() {
        return "dict/attr/show";
    }

    /**
     * 关联属性值
     * @param attrId
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/{attrId}")
    @RequiresPermissions(value = PermissionsCode.RELATED_ATTR_VALUE_BTN)
    public String toRelatedAttrValue(@PathVariable("attrId") Integer attrId, ModelMap model) throws Exception {
        model.put("attr", DozerUtil.map(attrApi.findAttrById(attrId), AttrVOResp.class));
        return "dict/attr/related";
    }

}
