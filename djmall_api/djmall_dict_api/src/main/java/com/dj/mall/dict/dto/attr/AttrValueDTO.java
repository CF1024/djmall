package com.dj.mall.dict.dto.attr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import java.io.Serializable;

/**
 * @author chengf
 * @date 2020/6/27 20:40
 * 商品属性值
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AttrValueDTO implements Serializable {
    /**
     * 商品属性值id
     */
    private Integer attrValueId;
    /**
     *商品属性ID
     */
    private Integer attrId;
    /**
     *商品属性值
     */
    private String attrValue;
}
