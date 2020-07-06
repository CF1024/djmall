/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_api
 * 类名：LastLoginTimeDTO
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chengf
 * @date 2020/6/3 15:06
 * 用户时报表DTOReq
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class LastLoginTimeDTO implements Serializable {
    /**
     * 主键id
     */
    private Integer timeId;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 用户id 外键
     */
    private Integer userId;
}
