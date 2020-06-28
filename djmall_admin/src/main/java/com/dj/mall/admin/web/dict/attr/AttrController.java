package com.dj.mall.admin.web.dict.attr;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.attr.AttrVOReq;
import com.dj.mall.admin.vo.dict.attr.AttrVOResp;
import com.dj.mall.dict.api.attr.AttrApi;
import com.dj.mall.dict.dto.attr.AttrDTO;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chengf
 * @date 2020/6/27 20:56
 * 商品属性
 */
@RestController
@RequestMapping("/dict/attr/")
public class AttrController {
    /**
     * 商品属性名api
     */
    @Reference
    private AttrApi attrApi;

    /**
     * 展示属性
     * @return
     * @throws Exception
     */
    @GetMapping
    @RequiresPermissions(value = PermissionsCode.ATTR_MANAGE)
    public ResultModel show() throws Exception {
        return new ResultModel().success(DozerUtil.mapList(attrApi.findAll(), AttrVOResp.class));
    }

    /**
     * 去重
     * @param attrId
     * @param attrName
     * @return
     * @throws Exception
     */
    @GetMapping("deDuplicate")
    public Boolean deDuplicate(Integer attrId, String attrName) throws Exception {
        return attrApi.deDuplicate(attrId, attrName);
    }

    /**
     * 新增
     * @param attrVOReq
     * @return
     * @throws Exception
     */
    @PostMapping
    @RequiresPermissions(value = PermissionsCode.ATTR_ADD_BTN)
    public ResultModel addAttr(AttrVOReq attrVOReq) throws Exception {
        attrApi.addAttr(DozerUtil.map(attrVOReq, AttrDTO.class));
        return new ResultModel().success("新增成功");
    }
}
