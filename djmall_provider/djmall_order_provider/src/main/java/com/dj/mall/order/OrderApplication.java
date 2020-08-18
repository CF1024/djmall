/*
 * 作者：CF
 * 日期：2020-07-29 17:23
 * 项目：djmall
 * 模块：djmall_order_provider
 * 类名：OrderApplication
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.order;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author chengf
 * @date 2020/7/29 17:24
 * 订单启动类
 */
@EnableTransactionManagement
@SpringBootApplication
@EnableDubbo
@MapperScan("com.dj.mall.order.mapper")
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
