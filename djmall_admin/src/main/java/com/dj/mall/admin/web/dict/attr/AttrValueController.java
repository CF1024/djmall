package com.dj.mall.admin.web.dict.attr;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.user.UserVOResp;
import com.dj.mall.admin.vo.dict.attr.AttrValueVOReq;
import com.dj.mall.admin.vo.dict.attr.AttrValueVOResp;
import com.dj.mall.dict.api.attr.AttrValueApi;
import com.dj.mall.dict.dto.attr.AttrValueDTO;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

/**
 * @author chengf
 * @date 2020/6/28 14:56
 * 商品属性值
 */
@RestController
@RequestMapping("/dict/attrValue/")
public class AttrValueController {
    /**
     * 属性值Api
     */
    @Reference
    private AttrValueApi attrValueApi;

    /**
     * 展示商品属性值
     * @param attrValueVOReq
     * @return
     * @throws Exception
     */
    @GetMapping
    @RequiresPermissions(value = PermissionsCode.RELATED_ATTR_VALUE_BTN)
    public ResultModel show(AttrValueVOReq attrValueVOReq) throws Exception {
        PageResult pageResult = attrValueApi.findAll(DozerUtil.map(attrValueVOReq, AttrValueDTO.class));
        pageResult.toBuilder().list(DozerUtil.mapList(pageResult.getList(), AttrValueVOResp.class)).build();
        return new ResultModel<>().success(pageResult);
    }

    /**
     * 去重
     * @param attrValue
     * @return
     * @throws Exception
     */
    @GetMapping("deDuplicate")
    public Boolean deDuplicate(String attrValue) throws Exception {
        return attrValueApi.deDuplicate(attrValue);
    }

    /**
     * 新增
     * @param attrValueVOReq
     * @return
     * @throws Exception
     */
    @PostMapping
    @RequiresPermissions(value = PermissionsCode.ATTR_VALUE_ADD_BTN)
    public ResultModel addAttrValue(AttrValueVOReq attrValueVOReq) throws Exception {
        attrValueApi.addAttrValue(DozerUtil.map(attrValueVOReq, AttrValueDTO.class));
        return new ResultModel<>().success("新增成功");
    }

    /**
     * 删除
     * @param attrValueId
     * @return
     * @throws Exception
     */
    @PostMapping("deleteAttrValue")
    @RequiresPermissions(value = PermissionsCode.ATTR_VALUE_DELETE_BTN)
    public ResultModel deleteAttrValue(Integer attrValueId) throws Exception {
        attrValueApi.deleteAttrValue(attrValueId);
        return new ResultModel<>().success("删除成功");
    }
}
