/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：FreightController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.web.dict.freight;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.freight.FreightVOReq;
import com.dj.mall.admin.vo.dict.freight.FreightVOResp;
import com.dj.mall.dict.api.freight.FreightApi;
import com.dj.mall.dict.dto.freight.FreightDTO;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

/**
 * @author chengf
 * @date 2020/6/16 14:20
 * 运费
 */
@RestController
@RequestMapping("/dict/freight/")
public class FreightController {
    /**
     * 运费Api
     */
    @Reference
    private FreightApi freightApi;

    /**
     * 展示运费
     * @return
     * @throws Exception
     */
    @GetMapping
    @RequiresPermissions(value = PermissionsCode.FREIGHT_MANAGE)
    public ResultModel<Object> show() throws Exception {
        return new ResultModel<>().success(DozerUtil.mapList(freightApi.findAll(), FreightVOResp.class));
    }

    /**
     * 新增
     * @param freightVOReq
     * @return
     * @throws Exception
     */
    @PostMapping
    @RequiresPermissions(value = PermissionsCode.FREIGHT_ADD_BTN)
    public ResultModel<Object> addFreight(FreightVOReq freightVOReq) throws Exception {
        freightApi.addFreight(DozerUtil.map(freightVOReq, FreightDTO.class));
        return new ResultModel<>().success("新增成功");
    }

    /**
     * 修改
     * @param freightVOReq
     * @return
     * @throws Exception
     */
    @PutMapping
    @RequiresPermissions(value = PermissionsCode.FREIGHT_UPDATE_BTN)
    public ResultModel<Object> updateFreight(FreightVOReq freightVOReq) throws Exception {
        freightApi.updateFreight(DozerUtil.map(freightVOReq, FreightDTO.class));
        return new ResultModel<>().success("修改成功");
    }
}
