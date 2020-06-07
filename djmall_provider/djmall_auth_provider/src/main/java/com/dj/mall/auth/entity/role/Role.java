package com.dj.mall.auth.entity.role;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import java.io.Serializable;

/**
 * @author chengf
 * @date 2020/6/3 13:53
 * 角色实体类
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("djmall_auth_role")
public class Role implements Serializable {

    /**
     * 角色id
     */
    @TableId(type = IdType.AUTO)
    @Mapping("roleId")
    private Integer id;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色状态 0 未删除 1 已删除
     */
    private String isDel;
}
