package com.dj.mall.auth.dto.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * @author chengf
 * @date 2020/6/5 13:36
 * 关联资源zTree
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TreeData implements Serializable {
    /**
     * 资源id
     */
    private Integer id;
    /**
     * 名字
     */
    private String name;
    /**
     * 父级ID
     */
    private Integer parentId;
    /**
     * 是否打开
     */
    private boolean open = true;
    /**
     * 默认勾选
     */
    private boolean checked = false;
}
