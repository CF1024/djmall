/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_cmpt_provider
 * 类名：CmptApplication
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author chengf
 * @date 2020/4/18 22:07
 * Redis启动类
 */
@EnableDubbo
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CmptApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmptApplication.class, args);
    }
}

