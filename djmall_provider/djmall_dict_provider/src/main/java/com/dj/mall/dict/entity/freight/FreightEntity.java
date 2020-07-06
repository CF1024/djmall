/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_dict_provider
 * 类名：FreightEntity
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.dict.entity.freight;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

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
@TableName("djmall_dict_product_freight")
public class FreightEntity implements Serializable {
    /**
     * 运费id
     */
    @TableId(type = IdType.AUTO)
    @Mapping("freightId")
    private Integer id;
    /**
     *物流 公司
     */
    private String company;
    /**
     *运费
     */
    private BigDecimal freight;

}
