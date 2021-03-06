package com.dj.mall.auth.entity.address;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("djmall_shipping_address")
public class UserAddressEntity implements Serializable {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
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

}
