/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_product_provider
 * 类名：ProductEntity
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.product.entity.spu;

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
 * @date 2020/7/2 11:47
 * 商品spu实体类
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("djmall_product_spu")
public class ProductEntity implements Serializable {
    /**
     * 商品spu的id
     */
    @TableId(type = IdType.AUTO)
    @Mapping("productId")
    private Integer id;
    /**
     *商品名
     */
    private String productName;
    /**
     *运费id
     */
    private Integer freightId;
    /**
     *商品描述
     */
    private String productDescribe;
    /**
     * 商品照片
     */
    private String productImg;
    /**
     * 商品类型
     */
    private String productType;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 商品状态
     */
    private String productStatus;
    /**
     * 订单量
     */
    private Integer orderNumber;
    /**
     *点赞量
     */
    private Integer praiseNumber;

}
