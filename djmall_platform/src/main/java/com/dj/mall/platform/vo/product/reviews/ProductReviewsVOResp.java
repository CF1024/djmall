/*
 * 作者：CF
 * 日期：2020-08-14 11:18
 * 项目：djmall
 * 模块：djmall_product_provider
 * 类名：ProductReviewsEntity
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.platform.vo.product.reviews;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chengf
 * @date 2020/8/14 14:53
 * 评论表
 */
@Data
@Accessors(chain = true)
public class ProductReviewsVOResp implements Serializable {
    /**
     * 回复集合
     */
    private List<ProductReviewsVOResp> replyList;
    /**
     * 好评率
     */
    private Integer goodRate;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 回复人id
     */
    private Integer reviewerId;

    /**
     * 评论内容
     */
    private String comment;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 回复id
     */
    private Integer replyId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 评分
     */
    private Double rate;
}
