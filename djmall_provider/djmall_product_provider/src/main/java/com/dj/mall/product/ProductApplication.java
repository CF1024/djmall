/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_product_provider
 * 类名：ProductApplication
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.product;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chengf
 * @date 2020/7/2 11:37
 * 商品启动类
 */
@EnableDubbo
@MapperScan("com.dj.mall.product.mapper")
@SpringBootApplication
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
}
