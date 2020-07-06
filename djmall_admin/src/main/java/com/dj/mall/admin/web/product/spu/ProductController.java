/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：ProductController
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.web.product.spu;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.attr.AttrVOResp;
import com.dj.mall.admin.vo.product.spu.ProductVOReq;
import com.dj.mall.admin.vo.product.spu.ProductVOResp;
import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.dict.api.attr.AttrApi;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.contant.AuthConstant;
import com.dj.mall.model.contant.DictConstant;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.product.api.spu.ProductApi;
import com.dj.mall.product.dto.spu.ProductDTO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author chengf
 * @date 2020/7/2 15:16
 * 商品Controller
 */
@RestController
@RequestMapping("/product/spu/")
public class ProductController {
    /**
     * 商品属性api
     */
    @Reference
    private AttrApi attrApi;
    /**
     * 商品api
     */
    @Reference
    private ProductApi productApi;

    /**
     * 商品展示
     * @param productVOReq 商品VOReq
     * @param session 用户session
     * @return ProductVOResp
     * @throws Exception 异常
     */
    @GetMapping
    public ResultModel<Object> show(ProductVOReq productVOReq, HttpSession session) throws Exception {
        //当前登录人是否为商人
        UserDTO userDTO = (UserDTO) session.getAttribute(AuthConstant.SESSION_USER);
        if (userDTO.getUserRole().equals(AuthConstant.BUSINESS)) {
            productVOReq.setUserId(userDTO.getUserId());
        }
        PageResult pageResult = productApi.findAll(DozerUtil.map(productVOReq, ProductDTO.class));
        return new ResultModel<>().success(pageResult.toBuilder().list(DozerUtil.mapList(pageResult.getList(), ProductVOResp.class)).build());
    }

    /**
     * 加载通用sku已关联的商品属性
     * @param productType 商品类型
     * @return AttrVOResp 商品属性voResp
     * @throws Exception 异常
     */
    @GetMapping("/{productType}")
    public ResultModel<Object> loadSkuGmRelatedAttr(@PathVariable("productType") String productType) throws Exception {
        return new  ResultModel<>().success(DozerUtil.mapList(attrApi.loadSkuGmRelatedAttr(productType), AttrVOResp.class));
    }

    /**
     * 去重
     * @param productVOReq 商品VOReq
     * @return Boolean：true && false
     * @throws Exception 异常
     */
    @GetMapping("deDuplicate")
    public Boolean deDuplicate(ProductVOReq productVOReq) throws Exception {
        return productApi.deDuplicate(DozerUtil.map(productVOReq, ProductDTO.class));
    }

    /**
     * 商品新增
     * @param productVOReq 商品VOReq
     * @param file 图片文件
     * @param session 用户session
     * @return ResultModel 新增成功
     * @throws Exception 异常
     */
    @PostMapping("addProduct")
    @RequiresPermissions(value = PermissionsCode.PRODUCT_ADD_BTN)
    public  ResultModel<Object> addProduct(ProductVOReq productVOReq, MultipartFile file, HttpSession session) throws Exception {
        Assert.notEmpty(productVOReq.getProductSkuList(), "请选择商品属性值并生成sku后再进行添加新的商品哟");
        //图片UUID 当前登录用户 默认上架 排除空数据
        String fileName = UUID.randomUUID().toString().replace("-", "") + Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
        UserDTO userDTO = (UserDTO) session.getAttribute(AuthConstant.SESSION_USER);

        productVOReq.setProductImg(fileName);
        productVOReq.setUserId(userDTO.getUserId());
        productVOReq.setProductStatus(DictConstant.PRODUCT_STATUS_UP);
        productVOReq.setProductSkuList(productVOReq.getProductSkuList().stream().filter(sku -> !StringUtils.isEmpty(sku.getSkuAttrIds())).collect(Collectors.toList()));

        productApi.addProduct(DozerUtil.map(productVOReq, ProductDTO.class), file.getBytes());
        return new  ResultModel<>().success("新增成功");
    }

    /**
     * 根据id修改商品和商品sku上下架状态
     * @param id 商品ID
     * @return 操作成功 修改无返回
     * @throws Exception 异常
     */
    @PostMapping("shelf")
    public  ResultModel<Object> shelf(Integer id) throws Exception {
        productApi.updateProductStatusById(id);
        return new  ResultModel<>().success("操作成功");
    }
}
