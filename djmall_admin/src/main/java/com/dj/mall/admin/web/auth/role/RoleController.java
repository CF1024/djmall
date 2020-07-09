/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：RoleController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.web.auth.role;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.role.RoleVOReq;
import com.dj.mall.admin.vo.auth.role.RoleVOResp;
import com.dj.mall.auth.api.role.RoleApi;
import com.dj.mall.auth.dto.resource.TreeData;
import com.dj.mall.auth.dto.role.RoleDTO;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/3 23:08
 * 角色控制层
 */
@RestController
@RequestMapping("/auth/role/")
public class RoleController {
    @Reference
    private RoleApi roleApi;

    /**
     * 角色展示
     * @param roleVOReq
     * @return
     * @throws Exception
     */
    @GetMapping
    @RequiresPermissions(value = PermissionsCode.ROLE_MANAGE)
    public ResultModel<Object> show(RoleVOReq roleVOReq) throws Exception {
        PageResult pageResult = roleApi.findAll(DozerUtil.map(roleVOReq, RoleDTO.class));
        pageResult.setList(DozerUtil.mapList(pageResult.getList(), RoleVOResp.class));
        return new ResultModel<>().success(pageResult);
    }

    /**
     * 去重
     * @param roleName
     * @param roleId
     * @return
     * @throws Exception
     */
    @GetMapping("deDuplicate")
    public Boolean deDuplicate(String roleName, Integer roleId) throws Exception {
        return roleApi.deDuplicate(roleName, roleId);
    }

    /**
     * 新增
     * @param roleVOReq
     * @return
     * @throws Exception
     */
    @PostMapping
    @RequiresPermissions(value = PermissionsCode.ROLE_ADD_BTN)
    public ResultModel<Object> addRole(RoleVOReq roleVOReq) throws Exception {
        roleApi.insertRole(DozerUtil.map(roleVOReq, RoleDTO.class));
        return new ResultModel<>().success("新增成功");
    }

    /**
     * 修改
     * @param roleVOReq
     * @return
     * @throws Exception
     */
    @PutMapping
    @RequiresPermissions(value = PermissionsCode.ROLE_UPDATE_BTN)
    public ResultModel<Object> updateRole(RoleVOReq roleVOReq) throws Exception {
        roleApi.updateRole(DozerUtil.map(roleVOReq, RoleDTO.class));
        return new ResultModel<>().success("修改成功");
    }

    /**
     * 删除
     * @param roleId
     * @return
     * @throws Exception
     */
    @PostMapping("remove")
    @RequiresPermissions(value = PermissionsCode.ROLE_DELETE_BTN)
    public ResultModel<Object> remove(Integer roleId) throws Exception {
        roleApi.removeRole(roleId);
        return new ResultModel<>().success("删除成功");
    }

    /**
     * 获取已关联资源
     * @param roleId
     * @return
     * @throws Exception
     */
    @GetMapping("/{roleId}")
    @RequiresPermissions(value = PermissionsCode.ROLE_RELATED_RESOURCE_BTN)
    public ResultModel<Object> getRelatedResource(@PathVariable("roleId") Integer roleId) throws Exception {
        return new ResultModel<>().success(roleApi.getRelatedResource(roleId));
    }

    /**
     * 关联资源
     * @param roleVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("saveRelevance")
    @RequiresPermissions(value = PermissionsCode.ROLE_RELATED_RESOURCE_BTN)
    public ResultModel<Object> saveRelevance(RoleVOReq roleVOReq) throws Exception {
        roleApi.saveRelevance(DozerUtil.map(roleVOReq, RoleDTO.class));
        return new ResultModel<>().success(true);
    }
}
