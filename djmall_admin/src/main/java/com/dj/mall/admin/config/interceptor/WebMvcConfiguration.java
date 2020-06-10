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
@DependsOn({"authInterceptor", "loginInterceptor"})
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //将spring注册到容器中
        InterceptorRegistration authInterceptorRegistration = registry.addInterceptor(authInterceptor);
        authInterceptorRegistration.addPathPatterns("/auth/user/toShow");
        authInterceptorRegistration.addPathPatterns("/auth/role/toShow");
        authInterceptorRegistration.addPathPatterns("/auth/resource/toShow");
        //将spring注册到容器中
        InterceptorRegistration loginInterceptorRegistration = registry.addInterceptor(loginInterceptor);
        //拦截的请求
        loginInterceptorRegistration.addPathPatterns("/**");
        //403拦截页面
        loginInterceptorRegistration.excludePathPatterns("/index/toDisplay");
        //js
        loginInterceptorRegistration.excludePathPatterns("/static/**");
        //去登陆
        loginInterceptorRegistration.excludePathPatterns("/auth/user/toLogin");
        //登录
        loginInterceptorRegistration.excludePathPatterns("/auth/user/login");
        //去注册
        loginInterceptorRegistration.excludePathPatterns("/auth/user/toAddUser");
        //注册
        loginInterceptorRegistration.excludePathPatterns("/auth/user/addUser");
        //去重
        loginInterceptorRegistration.excludePathPatterns("/auth/user/deDuplicate");
        //邮箱激活
        loginInterceptorRegistration.excludePathPatterns("/auth/user/toValidate/**");
        //去强制修改密码
        loginInterceptorRegistration.excludePathPatterns("/auth/user/toForceUpdatePwd");
        //强制修改密码
        loginInterceptorRegistration.excludePathPatterns("/auth/user/forceUpdatePwd");
        //去手机号登录
        loginInterceptorRegistration.excludePathPatterns("/auth/user/toPhoneLogin");
        //获取验证码
        loginInterceptorRegistration.excludePathPatterns("/auth/user/sendCode");
        //去手机号登录
        loginInterceptorRegistration.excludePathPatterns("/auth/user/phoneLogin");
        //去忘记密码
        loginInterceptorRegistration.excludePathPatterns("/auth/user/toForgetPwd");
        //忘记密码
        loginInterceptorRegistration.excludePathPatterns("/auth/user/forgetPwd");
    }
}
