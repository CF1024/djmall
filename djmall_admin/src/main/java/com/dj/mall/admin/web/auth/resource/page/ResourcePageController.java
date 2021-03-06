/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：ResourcePageController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.web.auth.resource.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.resource.ResourceVOResp;
import com.dj.mall.admin.vo.dict.dict.BaseDataVOResp;
import com.dj.mall.auth.api.resource.ResourceApi;
import com.dj.mall.dict.api.dict.BaseDataApi;
import com.dj.mall.model.contant.DictConstant;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chengf
 * @date 2020/6/3 23:09
 * 资源控制层
 */
@Controller
@RequestMapping("/auth/resource/")
public class ResourcePageController {
    /**
     * 资源api
     */
    @Reference
    private ResourceApi resourceApi;
    /**
     * 字典数据
     */
    @Reference
    private BaseDataApi baseDataApi;

    /**
     * 去展示
     * @return
     */
    @GetMapping("toShow")
    @RequiresPermissions(value = PermissionsCode.RESOURCE_MANAGE)
    public String toShow() {
        return "auth/resource/show";
    }

    /**
     * 去新增
     * @param parentId
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("toAdd/{parentId}")
    @RequiresPermissions(value = PermissionsCode.RESOURCE_ADD_BTN)
    public String toAdd(@PathVariable Integer parentId, Model model) throws Exception {
        model.addAttribute("parentId", parentId);
        model.addAttribute("resource", DozerUtil.map(resourceApi.findResourceByParentId(parentId), ResourceVOResp.class));
        model.addAttribute("typeList", DozerUtil.mapList(baseDataApi.findBaseDataByParentCode(DictConstant.RESOURCE_TYPE), BaseDataVOResp.class));
        return "auth/resource/add";
    }

    /**
     * 去修改资源
     * @param resourceId
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("toUpdate/{resourceId}")
    @RequiresPermissions(value = PermissionsCode.RESOURCE_UPDATE_BTN)
    public String toUpdate(@PathVariable Integer resourceId, Model model) throws Exception {
        model.addAttribute("resource", DozerUtil.map(resourceApi.findResourceByResourceId(resourceId), ResourceVOResp.class));
        model.addAttribute("typeList", DozerUtil.mapList(baseDataApi.findBaseDataByParentCode(DictConstant.RESOURCE_TYPE), BaseDataVOResp.class));
        return "auth/resource/update";
    }
}
