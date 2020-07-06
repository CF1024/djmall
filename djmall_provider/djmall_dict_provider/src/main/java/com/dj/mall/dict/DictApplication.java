/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_dict_provider
 * 类名：DictApplication
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.dict;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chengf
 * @date 2020/6/15 14:55
 * 字典启动类
 */
@EnableDubbo
@MapperScan("com.dj.mall.dict.mapper")
@SpringBootApplication
public class DictApplication {
    public static void main(String[] args) {
        SpringApplication.run(DictApplication.class, args);
    }

}
