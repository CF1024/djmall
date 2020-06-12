package com.dj.mall.admin.web.auth.resource.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.resource.ResourceVOResp;
import com.dj.mall.auth.api.resource.ResourceApi;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
     * 去展示
     * @return
     */
    @RequestMapping("toShow")
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
    @RequestMapping("toAdd/{parentId}")
    @RequiresPermissions(value = PermissionsCode.RESOURCE_ADD_BTN)
    public String toAdd(@PathVariable Integer parentId, Model model) throws Exception {
        model.addAttribute("parentId", parentId);
        model.addAttribute("resource", DozerUtil.map(resourceApi.findResourceByParentId(parentId), ResourceVOResp.class));
        return "auth/resource/add";
    }

    /**
     * 去修改资源
     * @param resourceId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toUpdate/{resourceId}")
    @RequiresPermissions(value = PermissionsCode.RESOURCE_UPDATE_BTN)
    public String toUpdate(@PathVariable Integer resourceId, Model model) throws Exception {
        model.addAttribute("resource", DozerUtil.map(resourceApi.findResourceByResourceId(resourceId), ResourceVOResp.class));
        return "auth/resource/update";
    }
}
