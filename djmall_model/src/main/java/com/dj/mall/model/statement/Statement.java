package com.dj.mall.model.statement;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author chengf
 * @date 2020/8/19 17:08
 * 统计报表
 */
@Data
@Accessors(chain = true)
public class Statement implements Serializable {

    /**
     * 日期 单位：天
     */
    private LocalDate days;

    /**
     * 数量
     */
    private Integer number;

    /**
     * 商品名
     */
    private String productName;
}
