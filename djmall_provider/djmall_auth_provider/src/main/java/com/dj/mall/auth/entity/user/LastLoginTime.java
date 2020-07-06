/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_provider
 * 类名：LastLoginTime
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chengf
 * @date 2020/6/3 13:53
 * 用户时报表
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("djmall_last_login_time")
public class LastLoginTime implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    @Mapping("timeId")
    private Integer id;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 用户id 外键
     */
    private Integer userId;
}
