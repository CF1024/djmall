/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_provider
 * 类名：AuthApplication
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chengf
 * @date 2020/6/3 12:48
 * 用户提供者启动类
 */
@EnableDubbo
@MapperScan("com.dj.mall.auth.mapper")
@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
