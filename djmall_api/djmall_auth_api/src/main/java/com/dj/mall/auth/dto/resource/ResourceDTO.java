package com.dj.mall.auth.dto.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chengf
 * @date 2020/6/3 22:50
 * 资源DTOReq 接收实体
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDTO implements Serializable {
    /**
     * 资源id
     */
    private Integer resourceId;

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
     * 资源状态
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