package com.dj.mall.admin.web.auth.resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.resource.ResourceVOReq;
import com.dj.mall.admin.vo.auth.resource.ResourceVOResp;
import com.dj.mall.auth.api.resource.ResourceApi;
import com.dj.mall.auth.dto.resource.ResourceDTO;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

/**
 * @author chengf
 * @date 2020/6/3 23:09
 * 资源控制层
 */
@RestController
@RequestMapping("/auth/resource/")
public class ResourceController {
    @Reference
    private ResourceApi resourceApi;

    /**
     * 资源展示
     * @return
     * @throws Exception
     */
    @PostMapping("show")
    @RequiresPermissions(value = PermissionsCode.RESOURCE_MANAGE)
    public ResultModel<Object> show() throws Exception {
        return new ResultModel<>().success(DozerUtil.mapList(resourceApi.findAll(), ResourceVOResp.class));
    }

    /**
     * 去重
     * @param resourceVOReq
     * @return
     * @throws Exception
     */
    @GetMapping("deDuplicate")
    public Boolean deDuplicate(ResourceVOReq resourceVOReq) throws Exception {
        return resourceApi.deDuplicate(DozerUtil.map(resourceVOReq, ResourceDTO.class));
    }

    /**
     * 资源新增
     * @param resourceVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("add")
    @RequiresPermissions(value = PermissionsCode.RESOURCE_ADD_BTN)
    public ResultModel<Object> add(ResourceVOReq resourceVOReq) throws Exception {
        resourceApi.insertResource(DozerUtil.map(resourceVOReq, ResourceDTO.class));
        return new ResultModel<>().success("新增资源成功");
    }

    /**
     * 修改
     * @param resourceVOReq
     * @return
     * @throws Exception
     */
    @PutMapping("update")
    @RequiresPermissions(value = PermissionsCode.RESOURCE_UPDATE_BTN)
    public ResultModel<Object> update(ResourceVOReq resourceVOReq) throws Exception {
        resourceApi.updateResource(DozerUtil.map(resourceVOReq, ResourceDTO.class));
        return new ResultModel<>().success("修改资源成功");
    }

}
