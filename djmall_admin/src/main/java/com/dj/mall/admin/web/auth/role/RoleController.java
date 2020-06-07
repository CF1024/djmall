package com.dj.mall.admin.web.auth.role;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.role.RoleVOReq;
import com.dj.mall.admin.vo.auth.role.RoleVOResp;
import com.dj.mall.auth.api.role.RoleApi;
import com.dj.mall.auth.dto.resource.TreeData;
import com.dj.mall.auth.dto.role.RoleDTO;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.util.DozerUtil;
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
    @PostMapping("show")
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
        Boolean deDuplicate = roleApi.deDuplicate(roleName, roleId);
        return deDuplicate;
    }

    /**
     * 新增
     * @param roleVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("add")
    public ResultModel<Object> add(RoleVOReq roleVOReq) throws Exception {
        roleApi.insertRole(DozerUtil.map(roleVOReq, RoleDTO.class));
        return new ResultModel<>().success("新增成功");
    }

    /**
     * 修改
     * @param roleVOReq
     * @return
     * @throws Exception
     */
    @PutMapping("update")
    public ResultModel<Object> update(RoleVOReq roleVOReq) throws Exception {
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
    @GetMapping("getRelatedResource/{roleId}")
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
    public ResultModel<Object> saveRelevance(RoleVOReq roleVOReq) throws Exception {
        roleApi.saveRelevance(DozerUtil.map(roleVOReq, RoleDTO.class));
        return new ResultModel<>().success(true);
    }
}
