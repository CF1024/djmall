/*
 * 作者：CF
 * 日期：2020-08-14 20:37
 * 项目：djmall
 * 模块：djmall_product_provider
 * 类名：ProductReviewsService
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.product.service.reviews.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.product.dto.reviews.ProductReviewsDTO;
import com.dj.mall.product.entity.reviews.ProductReviewsEntity;
import com.dj.mall.product.mapper.reviews.ProductReviewsMapper;
import com.dj.mall.product.service.reviews.ProductReviewsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chengf
 * @date 2020/8/14 20:37
 * 评论service实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductReviewsServiceImpl extends ServiceImpl<ProductReviewsMapper, ProductReviewsEntity> implements ProductReviewsService {
    /**
     * 根据回复id查评论集合
     * @param idList 回复id
     * @return  List<ProductReviewsDTO>
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public List<ProductReviewsDTO> findReviewsByReplyId(List<Integer> idList) throws Exception, BusinessException {
        return DozerUtil.mapList(baseMapper.findReviewsByReplyId(idList), ProductReviewsDTO.class);
    }
}
