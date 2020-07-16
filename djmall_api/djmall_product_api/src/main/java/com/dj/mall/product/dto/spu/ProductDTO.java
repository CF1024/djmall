/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_product_api
 * 类名：ProductDTO
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.product.dto.spu;

import com.dj.mall.product.dto.sku.SkuDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class ProductDTO implements Serializable {
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
     * 当前页
     */
    private Integer pageNo = 1;
    /**
     * 每页条数
     */
    private Integer pageSize = 5;
    /**
     * 商品类型集合 展示商品 复选框查询
     */
    private List<String> productTypeList;
    /**
     * 公司 展示商品
     */
    private String company;
    /**
     * 运费 展示商品
     */
    private BigDecimal freightShow;
    /**
     * 生成sku集合 新增商品
     */
    private List<SkuDTO> productSkuList;
    /**
     * 删除商品照片 修改商品
     */
    private String removeImg;

    /*=========================商城========================*/
    /**
     * 最低价格 展示 价格查询
     */
    private BigDecimal skuPriceMin;
    /**
     * 最高价格 展示 价格查询
     */
    private BigDecimal skuPriceMax;
    /**
     * 是否默认：是 展示条件
     */
    private String isDefault;
    /**
     * 库存 展示
     */
    private Integer skuCountShow;
    /**
     * 价格 展示
     */
    private BigDecimal skuPriceShow;
    /**
     *  折扣 展示
     */
    private String skuRateShow;
}
