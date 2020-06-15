package com.dj.mall.admin.vo.dict.dict;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chengf
 * @date 2020/6/15 15:07
 * 基础数据实体类
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BaseDataVOResp implements Serializable {
    /**
     * 基础编码
     */
    private String baseCode;
    /**
     * 基础名
     */
    private String baseName;
    /**
     * 父级编码
     */
    private String parentCode;
}
