package com.dj.mall.admin.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author chengf
 * @date 2020/6/6 13:24
 * 自定义拦截器
 */
@Configuration
@DependsOn("myInterceptor")
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private MyInterceptor myInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //将spring注册到容器中
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(myInterceptor);
        //拦截的请求
        interceptorRegistration.addPathPatterns("/**");
        //放过的请求
        //js
        interceptorRegistration.excludePathPatterns("/static/**");
        //去登陆
        interceptorRegistration.excludePathPatterns("/auth/user/toLogin");
        //登录
        interceptorRegistration.excludePathPatterns("/auth/user/login");
        //去注册
        interceptorRegistration.excludePathPatterns("/auth/user/toAddUser");
        //注册
        interceptorRegistration.excludePathPatterns("/auth/user/addUser");
        //去重
        interceptorRegistration.excludePathPatterns("/auth/user/deDuplicate");
        //邮箱激活
        interceptorRegistration.excludePathPatterns("/auth/user/toValidate/**");
        //去强制修改密码
        interceptorRegistration.excludePathPatterns("/auth/user/toForceUpdatePwd");
        //强制修改密码
        interceptorRegistration.excludePathPatterns("/auth/user/forceUpdatePwd");
        //去手机号登录
        interceptorRegistration.excludePathPatterns("/auth/user/toPhoneLogin");
        //获取验证码
        interceptorRegistration.excludePathPatterns("/auth/user/sendCode");
        //去手机号登录
        interceptorRegistration.excludePathPatterns("/auth/user/phoneLogin");
        //去忘记密码
        interceptorRegistration.excludePathPatterns("/auth/user/toForgetPwd");
        //忘记密码
        interceptorRegistration.excludePathPatterns("/auth/user/forgetPwd");
    }
}
