/*
 * 作者：CF
 * 日期：2020-07-18 20:50
 * 项目：djmall
 * 模块：djmall_platform
 * 类名：WebMvcConfiguration
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.platform.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@DependsOn("tokenInterceptor")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(tokenInterceptor);
        // 拦截请求
        interceptorRegistration.addPathPatterns("/**");
        // 放过请求 static error
        interceptorRegistration.excludePathPatterns("/error");
        interceptorRegistration.excludePathPatterns("/static/**");
        // 放过请求 用户
        interceptorRegistration.excludePathPatterns("/user/toLogin");
        interceptorRegistration.excludePathPatterns("/user/login");
        interceptorRegistration.excludePathPatterns("/user/toRegister");
        interceptorRegistration.excludePathPatterns("/user/");
        interceptorRegistration.excludePathPatterns("/user/getVerifyCode");
        interceptorRegistration.excludePathPatterns("/user/sendCode");
        interceptorRegistration.excludePathPatterns("/user/phoneLogin");
        // 放过请求 商城
        interceptorRegistration.excludePathPatterns("/product/toShow");
        interceptorRegistration.excludePathPatterns("/product/");
        interceptorRegistration.excludePathPatterns("/product/toProductDetails");
        interceptorRegistration.excludePathPatterns("/product/getSkuBySkuId");
    }
}
