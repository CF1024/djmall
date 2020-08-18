/*
 * 作者：CF
 * 日期：2020-07-10 18:08
 * 项目：djmall
 * 模块：djmall_platform
 * 类名：ProductController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.platform.web.product;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.cmpt.RedisApi;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.contant.ProductConstant;
import com.dj.mall.model.contant.RedisConstant;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.platform.vo.product.reviews.ProductReviewsVOReq;
import com.dj.mall.platform.vo.product.reviews.ProductReviewsVOResp;
import com.dj.mall.platform.vo.product.sku.SkuVOResp;
import com.dj.mall.platform.vo.product.spu.ProductVOReq;
import com.dj.mall.platform.vo.product.spu.ProductVOResp;
import com.dj.mall.product.api.sku.SkuApi;
import com.dj.mall.product.api.spu.ProductApi;
import com.dj.mall.product.dto.reviews.ProductReviewsDTO;
import com.dj.mall.product.dto.spu.ProductDTO;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product/")
public class ProductController {
    /**
     * 商品api
     */
    @Reference
    private ProductApi productApi;
    /**
     * 商品skuApi
     */
    @Reference
    private SkuApi skuApi;
    /**
     * redisApi
     */
    @Reference
    private RedisApi redisApi;

    /**
     * 商城展示
     * @param productVOReq  productVOReq
     * @return ResultModel
     * @throws Exception 异常信息
     */
    @GetMapping
    public ResultModel<Object> show(ProductVOReq productVOReq, String TOKEN) throws Exception {
        //得到当前登录用户id
        if (!StringUtils.isEmpty(TOKEN)) {
            UserDTO user = redisApi.get(RedisConstant.USER_TOKEN + TOKEN);
            productVOReq.setCurrentlyLoggedInUserId(user.getUserId());
        }
        PageResult pageResult = productApi.findList(DozerUtil.map(productVOReq, ProductDTO.class));
        return new ResultModel<>().success(pageResult.toBuilder().list(DozerUtil.mapList(pageResult.getList(), ProductVOResp.class)).build());
    }

    /**
     *
     * 根据商品skuId查找sku数据
     * @return SkuVOResp
     * @throws Exception 异常
     */
    @GetMapping("getSkuBySkuId")
    public ResultModel<Object> getSkuBySkuId(Integer skuId) throws Exception {
        return new ResultModel<>().success(DozerUtil.map(skuApi.getSkuBySkuId(skuId), SkuVOResp.class));
    }

    /**
     * 用户点赞
     * @param productId 商品id
     * @param isLike 是否点赞 0 是 1 否
     * @param token 用户唯一标识
     * @return ResultModel
     * @throws Exception 异常
     */
    @PostMapping("like")
    public ResultModel<Object> like(Integer productId, Integer isLike, @RequestHeader(ProductConstant.TOKEN) String token) throws Exception {
        UserDTO user = redisApi.get(RedisConstant.USER_TOKEN + token);
        productApi.like(productId, isLike, user.getUserId());
        return new ResultModel<>().success();
    }

    /**
     *
     * @param productReviewsVOReq 商品评论vo
     * @param token 用户唯一标识
     * @return ResultModel
     * @throws Exception 异常
     */
    @PostMapping("addComment")
    public ResultModel<Object> addComment(ProductReviewsVOReq productReviewsVOReq, @RequestHeader(ProductConstant.TOKEN) String token) throws Exception {
        UserDTO user = redisApi.get(RedisConstant.USER_TOKEN + token);
        productApi.addComment(DozerUtil.map(productReviewsVOReq, ProductReviewsDTO.class), user.getUserId());
        return new ResultModel<>().success();
    }

    /**
     * 查看评论
     * @param productReviewsVOReq 商品评论 vo
     * @return  评论数据
     * @throws Exception 异常
     */
    @PostMapping("getCommentByProductId")
    public ResultModel<Object> getCommentByProductId(ProductReviewsVOReq productReviewsVOReq) throws Exception {
        PageResult pageResult = productApi.findReviewsByProductId(DozerUtil.map(productReviewsVOReq, ProductReviewsDTO.class));
        return new ResultModel<>().success(pageResult.toBuilder().list(DozerUtil.mapList(pageResult.getList(), ProductReviewsVOResp.class)).build());
    }

}
