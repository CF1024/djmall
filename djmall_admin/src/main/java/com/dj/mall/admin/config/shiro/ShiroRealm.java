/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：ShiroRealm
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.admin.config.shiro;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.auth.dto.resource.ResourceDTO;
import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.cmpt.RedisApi;
import com.dj.mall.model.contant.AuthConstant;
import com.dj.mall.model.contant.RedisConstant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/11 15:00
 * shiro权限控制 自定义Realm
 */
@Component
public class ShiroRealm extends AuthorizingRealm {
    /**
     * redis的Api
     */
    @Reference
    private RedisApi redisApi;
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        UserDTO USER = (UserDTO) subject.getSession().getAttribute(AuthConstant.SESSION_USER);
        List<ResourceDTO> resourceList = redisApi.getHash(RedisConstant.ROLE_ALL_KEY, RedisConstant.ROLE_ID_KEY + USER.getUserRole());
        resourceList.forEach(resource -> simpleAuthorizationInfo.addStringPermission(resource.getResourceCode()));
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 得到用户名
        String username = (String) authenticationToken.getPrincipal();
        // 得到密码
        String password = new String((char[]) authenticationToken.getCredentials());
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
