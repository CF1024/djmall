/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：BaseDataPageController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.web.dict.dict.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.dict.BaseDataVOResp;
import com.dj.mall.dict.api.dict.BaseDataApi;
import com.dj.mall.model.contant.DictConstant;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chengf
 * @date 2020/6/15 15:26
 * 字典数据
 */
@Controller
@RequestMapping("/dict/base/")
public class BaseDataPageController {
    /**
     * 字典api
     */
    @Reference
    private BaseDataApi baseDataApi;

    /**
     * 去展示字典
     * @return
     */
    @GetMapping("toShow")
    @RequiresPermissions(value = PermissionsCode.DICT_MANAGE)
    public String toShow(ModelMap model) throws Exception {
        model.put("systemList", DozerUtil.mapList(baseDataApi.findBaseDataByParentCode(DictConstant.SYSTEM), BaseDataVOResp.class));
        return "dict/dict/show";
    }

    /**
     * 去修改
     * @param baseCode 基础编码
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("{baseCode}")
    @RequiresPermissions(value = PermissionsCode.DICT_UPDATE_BTN)
    public String toUpdate(@PathVariable("baseCode") String baseCode, ModelMap model) throws Exception {
        model.put("baseData", DozerUtil.map(baseDataApi.findBaseDataByBaseCode(baseCode), BaseDataVOResp.class));
        return "dict/dict/update";
    }

}
