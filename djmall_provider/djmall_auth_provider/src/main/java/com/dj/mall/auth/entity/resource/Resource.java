package com.dj.mall.auth.entity.resource;

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
 * 资源实体类
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("djmall_auth_resource")
public class Resource implements Serializable {

    /**
     * 资源id
     */
    @TableId(type = IdType.AUTO)
    @Mapping("resourceId")
    private Integer id;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     *资源路径
     */
    private String url;

    /**
     * 资源父级
     */
    private Integer parentId;

    /**
     * 资源状态 0 未删除 1 已删除
     */
    private String isDel;
    /**
     * 资源类型
     */
    private String resourceType;
    /**
     * 资源编码
     */
    private String resourceCode;
}
