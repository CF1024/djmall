/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：ShiroConfiguration
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.config.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author chengf
 * @date 2020/6/11 15:06
 * shiro配置
 */
@Configuration
@DependsOn("shiroRealm")
public class ShiroConfiguration {
    /**
     * 自定义Realm
     */
    @Autowired
    private ShiroRealm shiroRealm;

    /**
     * 安全管理器
     * @return
     */
    @Bean
    DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);
        return securityManager;
    }

    /**
     * shiro过滤器工厂
     * @param securityManager
     * @return
     */
    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/auth/user/toLogin");
        shiroFilterFactoryBean.setSuccessUrl("/index/toIndex");
        shiroFilterFactoryBean.setUnauthorizedUrl("/index/toDisPlay");
        Map<String, String> filters = new LinkedHashMap<>();
        //放过的请求 js 登录 去注册 注册 去重 去激活 去强制修改密码 强制修改密码 去手机号登录  手机号登录 获取验证码 去忘记密码 忘记密码
        filters.put("/static/**", "anon");
        filters.put("/auth/user/login", "anon");
        filters.put("/auth/user/toAddUser", "anon");
        filters.put("/auth/user/addUser", "anon");
        filters.put("/auth/user/deDuplicate", "anon");
        filters.put("/auth/user/toValidate/**", "anon");
        filters.put("/auth/user/toForceUpdatePwd", "anon");
        filters.put("/auth/user/forceUpdatePwd", "anon");
        filters.put("/auth/user/toPhoneLogin", "anon");
        filters.put("/auth/user/sendCode", "anon");
        filters.put("/auth/user/phoneLogin", "anon");
        filters.put("/auth/user/toForgetPwd", "anon");
        filters.put("/auth/user/forgetPwd", "anon");
        //拦截的请求
        filters.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filters);
        return shiroFilterFactoryBean;
    }
}
