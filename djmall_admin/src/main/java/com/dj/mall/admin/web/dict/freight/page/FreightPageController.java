package com.dj.mall.admin.web.dict.freight.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.dict.BaseDataVOResp;
import com.dj.mall.dict.api.dict.BaseDataApi;
import com.dj.mall.dict.api.freight.FreightApi;
import com.dj.mall.dict.dto.freight.FreightDTO;
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
 * @date 2020/6/16 14:20
 * 运费
 */
@Controller
@RequestMapping("/dict/freight/")
public class FreightPageController {
    /**
     * 字典api
     */
    @Reference
    private BaseDataApi baseDataApi;
    /**
     * 运费api
     */
    @Reference
    private FreightApi freightApi;

    /**
     * 去展示运费
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("toShow")
    @RequiresPermissions(value = PermissionsCode.FREIGHT_MANAGE)
    public String toShow(ModelMap model) throws Exception {
        //运费
        model.put("freightList", DozerUtil.mapList(baseDataApi.findBaseDataByParentCode(DictConstant.LOGISTICS_COMPANY), BaseDataVOResp.class));
        return "dict/freight/show";
    }

    /**
     *根据id查
     * @param freightId
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/{freightId}")
    @RequiresPermissions(value = PermissionsCode.FREIGHT_UPDATE_BTN)
    public String toShow(@PathVariable("freightId") Integer freightId, ModelMap model) throws Exception {
        model.put("freight", DozerUtil.map(freightApi.findFreightById(freightId), FreightDTO.class));
        return "dict/freight/update";
    }
}
