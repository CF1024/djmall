package com.dj.mall.admin.vo.auth.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chengf
 * @date 2020/6/3 14:39
 * 用户时报表VOReq
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class LastLoginTimeVOReq implements Serializable {
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
