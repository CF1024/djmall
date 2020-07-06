/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_dict_api
 * 类名：FreightDTO
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.dict.dto.freight;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chengf
 * @date 2020/6/16 14:21
 * 运费实体类
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class FreightDTO implements Serializable {
    /**
     * 运费id
     */
    private Integer freightId;
    /**
     *物流公司
     */
    private String company;
    /**
     *运费
     */
    private BigDecimal freight;
}
