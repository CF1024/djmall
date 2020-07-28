package com.dj.mall.platform.vo.auth.address;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author chengf
 * @date 2020/7/27 20:49
 * 地区表
 */
@Data
@Accessors(chain = true)
public class AreaVoReq implements Serializable {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 地区父id
     */
    private Integer areaParentId;

    /**
     * 地区名
     */
    private String areaName;

    /**
     * 地区类型 省/市/县
     */
    private String areaType;
    /**
     * 状态
     */
    private String isDel;


}
