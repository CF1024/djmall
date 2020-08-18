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
import com.dj.mall.admin.vo.dict.sku.SkuGmVOResp;
import com.dj.mall.admin.vo.product.reviews.ProductReviewsVOReq;
import com.dj.mall.admin.vo.product.reviews.ProductReviewsVOResp;
import com.dj.mall.admin.vo.product.spu.ProductVOReq;
import com.dj.mall.admin.vo.product.spu.ProductVOResp;
import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.dict.api.attr.AttrApi;
import com.dj.mall.dict.api.sku.SkuGmApi;
import com.dj.mall.dict.dto.sku.SkuGmDTO;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.contant.AuthConstant;
import com.dj.mall.model.contant.DictConstant;
import com.dj.mall.model.contant.PermissionsCode;
import com.dj.mall.model.contant.ProductConstant;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.product.api.spu.ProductApi;
import com.dj.mall.product.dto.reviews.ProductReviewsDTO;
import com.dj.mall.product.dto.spu.ProductDTO;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.*;
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
     * skuGmApi
     */
    @Reference
    private SkuGmApi skuGmApi;

    /**
     * 商品展示
     * @param productVOReq 商品VOReq
     * @param session 用户session
     * @return ProductVOResp
     * @throws Exception 异常
     */
    @GetMapping
    @RequiresPermissions(value = PermissionsCode.PRODUCT_MANAGE)
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
    @PostMapping
    @RequiresPermissions(value = PermissionsCode.PRODUCT_ADD_BTN)
    public  ResultModel<Object> addProduct(ProductVOReq productVOReq, MultipartFile file, HttpSession session) throws Exception {
        Assert.notEmpty(productVOReq.getProductSkuList(), "请选择商品属性值并生成sku后再进行添加新的商品哟");
        //图片UUID 当前登录用户 默认上架 订单量 点赞量 评论量 创建时间  排除空数据
        String fileName = UUID.randomUUID().toString().replace("-", "") + Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
        UserDTO userDTO = (UserDTO) session.getAttribute(AuthConstant.SESSION_USER);

        productVOReq.setProductImg(fileName);
        productVOReq.setUserId(userDTO.getUserId());
        productVOReq.setCreateTime(LocalDateTime.now());
        productVOReq.setOrderNumber(ProductConstant.ZERO);
        productVOReq.setPraiseNumber(ProductConstant.ZERO);
        productVOReq.setReviewsNumber(ProductConstant.ZERO);
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
    @RequiresPermissions(value = PermissionsCode.PRODUCT_SHELF_BTN)
    public  ResultModel<Object> shelf(Integer id) throws Exception {
        productApi.updateProductStatusById(id);
        return new  ResultModel<>().success("操作成功");
    }

    /**
     * 根据ID修改商品
     * @param productVOReq productVOReq
     * @param file 图片文件流
     * @return 修改成功
     * @throws Exception 异常
     */
    @PutMapping
    @RequiresPermissions(value = PermissionsCode.PRODUCT_UPDATE_BTN)
    public ResultModel<Object> updateProduct(ProductVOReq productVOReq, MultipartFile file) throws Exception {
        //判断file文件流是不是空
        byte[] bytes = null;
        if (!StringUtils.isEmpty(file.getOriginalFilename())) {
            String fileName = UUID.randomUUID().toString().replace("-", "") + Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
            productVOReq.setProductImg(fileName);
            bytes = file.getBytes();
        }
        productApi.updateProductById(DozerUtil.map(productVOReq, ProductDTO.class), bytes);
        return new  ResultModel<>().success("修改成功");
    }

    /**
     * 增量索引
     * @throws Exception 异常
     */
    @PostMapping("incrementalIndex")
    @RequiresPermissions(value = PermissionsCode.INCREMENTAL_INDEX_BTN)
    public ResultModel<Object> incrementalIndex() throws Exception {
        productApi.incrementalIndex();
        return new  ResultModel<>().success();
    }

    /**
     * 重构索引
     * @throws Exception 异常
     */
    @PostMapping("refactoringTheIndex")
    @RequiresPermissions(value = PermissionsCode.REFACTORING_THE_INDEX_BTN)
    public ResultModel<Object> refactoringTheIndex() throws Exception {
        productApi.refactoringTheIndex();
        return new  ResultModel<>().success();
    }

    /**
     * 下载导入模板
     * @param response HttpServletResponse
     * @throws Exception 异常
     */
    @GetMapping("toDownloadTheImportTemplate")
    @RequiresPermissions(value = PermissionsCode.DOWNLOAD_THE_IMPORT_TEMPLATE_BTN)
    public void toDownloadTheImportTemplate(HttpServletResponse response) throws Exception {
        List<SkuGmVOResp> skuGmList = DozerUtil.mapList(skuGmApi.findSkuGm(), SkuGmVOResp.class);
        XSSFWorkbook workbook = new XSSFWorkbook();
        skuGmList.forEach(skuGm -> {  
            // 新建页
            XSSFSheet sheet = workbook.createSheet(skuGm.getProductType());
            sheet.setColumnWidth(1, 20*256);// 给商品描述设置10个汉字宽度
            // 创建行
            XSSFRow row = sheet.createRow(0);
            // 新建第一列
            XSSFCell cell = row.createCell(0);
            // 设置值
            cell.setCellValue("商品名");
            row.createCell(1).setCellValue("商品描述");
            row.createCell(2).setCellValue("SKU信息");
            row.createCell(3).setCellValue("库存");
            row.createCell(4).setCellValue("价格（元）");
            row.createCell(5).setCellValue("折扣（%）");
        });
        OutputStream out = response.getOutputStream();
        String fileName = "商品导入模板.xlsx";
        String fileNameURL = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileNameURL+";"+"filename*=utf-8''"+fileNameURL);
        workbook.write(out);
        out.close();
    }

    /**
     * 导入
     * @param userDTO 当前登录用户
     * @param file 文件
     * @return ResultModel
     * @throws Exception 异常
     */
    @PostMapping("importProduct")
    public ResultModel<Object> importProduct(@SessionAttribute(AuthConstant.SESSION_USER) UserDTO userDTO, MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            return new ResultModel<>().error("上传文件不能为空");
        }
        String fileName = file.getOriginalFilename();
        productApi.importProduct(file.getBytes(), userDTO.getUserId(), fileName);
        return new ResultModel<>().success(true);
    }

    /**
     * 查看评论
     * @param productReviewsVOReq 商品评论 vo
     * @return  评论数据
     * @throws Exception 异常
     */
    @PostMapping("getCommentByProductId")
    @RequiresPermissions(value = PermissionsCode.VIEW_COMMENTS_BTN)
    public ResultModel<Object> getCommentByProductId(ProductReviewsVOReq productReviewsVOReq) throws Exception {
        PageResult pageResult = productApi.findReviewsByProductId(DozerUtil.map(productReviewsVOReq, ProductReviewsDTO.class));
        return new ResultModel<>().success(pageResult.toBuilder().list(DozerUtil.mapList(pageResult.getList(), ProductReviewsVOResp.class)).build());
    }

    /**
     * 新增回复
     * @param productReviewsVOReq 商品评论 vo
     * @param userDTO 当前登录商家
     * @return  ResultModel
     * @throws Exception 异常
     */
    @PostMapping("addReply")
    public ResultModel<Object> addReply(ProductReviewsVOReq productReviewsVOReq, @SessionAttribute(AuthConstant.SESSION_USER) UserDTO userDTO) throws Exception {
        productApi.addReply(DozerUtil.map(productReviewsVOReq, ProductReviewsDTO.class), userDTO.getUserId());
        return new ResultModel<>().success();
    }
}
