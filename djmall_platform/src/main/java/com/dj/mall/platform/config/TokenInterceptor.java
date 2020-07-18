/*
 * 作者：CF
 * 日期：2020-07-18 20:50
 * 项目：djmall
 * 模块：djmall_platform
 * 类名：TokenInterceptor
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.platform.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.cmpt.RedisApi;
import com.dj.mall.model.contant.RedisConstant;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final String TOKEN = "TOKEN";

    @Reference
    private RedisApi redisApi;

    private boolean checkToken(String token) {
        if (null != token) {
            // token 有效性验证
            UserDTO userDTO = redisApi.get(RedisConstant.USER_TOKEN + token);
            if (null != userDTO) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 是ajax请求 从Harder中获取Token信息
        if (httpServletRequest.getHeader("x-requested-with") != null && httpServletRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            // 从request的heard中获取token
            String token = httpServletRequest.getHeader(TOKEN);
            if (checkToken(token)) {
                return true;
            }
            // 状态码随意 但是不能是Http预设的状态码
            httpServletResponse.setStatus(666);
            return false;
        } else {
            // 获取请求中的token
            String token = httpServletRequest.getParameter(TOKEN);
            if (checkToken(token)) {
                return true;
            }
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/user/toLogin");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
