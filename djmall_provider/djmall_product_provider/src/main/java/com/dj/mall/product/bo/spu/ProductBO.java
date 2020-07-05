package com.dj.mall.product.bo.spu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author chengf
 * @date 2020/7/2 11:47
 * 商品spu
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductBO implements Serializable {
    /**
     * 商品spu的id
     */
    private Integer productId;
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
    /**
     * 商品类型集合
     */
    private List<String> productTypeList;
    /**
     * 公司
     */
    private String company;
    /**
     * 运费
     */
    private String freightShow;
}
