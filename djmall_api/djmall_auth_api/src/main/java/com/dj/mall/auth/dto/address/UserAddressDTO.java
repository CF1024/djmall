package com.dj.mall.auth.dto.address;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author chengf
 * @date 2020/7/27 20:50
 * 用户收货地址
 */
@Data
@Accessors(chain = true)
public class UserAddressDTO implements Serializable {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 省
     */
    private Integer province;

    /**
     * 市
     */
    private Integer city;

    /**
     * 县/区
     */
    private Integer county;

    /**
     * 详细地址
     */
    private String detailedAddress;

    /**
     * 收货人姓名
     */
    private String receiverName;
    /**
     * 状态
     */
    private String isDel;

    /**
     * 省
     */
    private String provinceShow;
    /**
     * 市
     */
    private String cityShow;
    /**
     * 县
     */
    private String countyShow;

}
