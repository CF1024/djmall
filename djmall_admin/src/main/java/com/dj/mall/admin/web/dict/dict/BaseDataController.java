package com.dj.mall.admin.web.dict.dict;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.dict.BaseDataVOReq;
import com.dj.mall.admin.vo.dict.dict.BaseDataVOResp;
import com.dj.mall.dict.api.dict.BaseDataApi;
import com.dj.mall.dict.dto.dict.BaseDataDTO;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

/**
 * @author chengf
 * @date 2020/6/15 15:26
 * 字典数据
 */
@RestController
@RequestMapping("/dict/base/")
public class BaseDataController {
    /**
     * 字典api
     */
    @Reference
    private BaseDataApi baseDataApi;

    /**
     * 展示字典
     * @param baseDataVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("show")
    @RequiresPermissions(value = PermissionsCode.DICT_MANAGE)
    public ResultModel<Object> show(BaseDataVOReq baseDataVOReq) throws Exception {
        PageResult pageResult = baseDataApi.findAll(DozerUtil.map(baseDataVOReq, BaseDataDTO.class));
        return new ResultModel<>().success(pageResult.toBuilder().list(DozerUtil.mapList(pageResult.getList(), BaseDataVOResp.class)).build());
    }

    /**
     * 去重
     * @param baseDataVOReq
     * @return
     * @throws Exception
     */
    @GetMapping("deDuplicate")
    public Boolean deDuplicate(BaseDataVOReq baseDataVOReq) throws Exception {
        return baseDataApi.deDuplicate(DozerUtil.map(baseDataVOReq, BaseDataDTO.class));
    }

    /**
     * 新增
     * @param baseDataVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("addBase")
    @RequiresPermissions(value = PermissionsCode.DICT_ADD_BTN)
    public ResultModel<Object> addBase(BaseDataVOReq baseDataVOReq) throws Exception {
        baseDataApi.addBase(DozerUtil.map(baseDataVOReq, BaseDataDTO.class));
        return new ResultModel<>().success("新增成功");
    }

    /**
     * 修改
     * @param baseDataVOReq
     * @return
     * @throws Exception
     */
    @PutMapping("updateBase")
    @RequiresPermissions(value = PermissionsCode.DICT_UPDATE_BTN)
    public ResultModel<Object> updateBase(BaseDataVOReq baseDataVOReq) throws Exception {
        baseDataApi.updateBase(DozerUtil.map(baseDataVOReq, BaseDataDTO.class));
        return new ResultModel<>().success("修改成功");
    }
}
