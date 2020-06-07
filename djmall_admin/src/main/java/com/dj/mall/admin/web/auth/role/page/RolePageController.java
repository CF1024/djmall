package com.dj.mall.admin.web.auth.role.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.role.RoleVOResp;
import com.dj.mall.auth.api.role.RoleApi;
import com.dj.mall.auth.dto.resource.TreeData;
import com.dj.mall.auth.dto.role.RoleDTO;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/3 23:08
 * 角色控制层
 */
@Controller
@RequestMapping("/auth/role/")
public class RolePageController {
    @Reference
    private RoleApi roleApi;

    /**
     * 去展示角色页面
     * @return
     */
    @RequestMapping("toShow")
    public String toShow() {
        return "auth/role/show";
    }

    /**
     * 去新增页面
     * @return
     */
    @RequestMapping("toAdd")
    public String toAdd() {
        return "auth/role/add";
    }

    /**
     * 去修改页面
     * @param roleId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toUpdate/{roleId}")
    public String toUpdate(@PathVariable("roleId") Integer roleId, Model model) throws Exception {
        model.addAttribute("role", DozerUtil.map(roleApi.findById(roleId), RoleVOResp.class));
        return "auth/role/update";
    }

    /**
     * 去关联资源
     * @param roleId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toRelatedResource/{roleId}")
    public String toRelatedResource(@PathVariable("roleId") Integer roleId, ModelMap model) {
        model.put("roleId", roleId);
        return "auth/role/related_resource";
    }
}
