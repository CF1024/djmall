package com.dj.mall.dict.bo.freight;

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
public class FreightBO implements Serializable {
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