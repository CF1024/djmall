/*
 * 作者：CF
 * 日期：2020-07-12 15:30
 * 项目：djmall
 * 模块：djmall_auth_api
 * 类名：UserTokenVOResp
 * 版权所有(C), 2020. 所有权利保留
 */

/*
 * 作者：CF
 * 日期：2020-07-12 14:35
 * 项目：djmall
 * 模块：djmall_platform
 * 类名：UserTokenVOResp
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chengf
 * @date 2020/6/3 14:35
 * 用户tokenDto
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenDTO implements Serializable {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * token
     */
    private String token;

    /**
     * 昵称
     */
    private String nickName;

}
