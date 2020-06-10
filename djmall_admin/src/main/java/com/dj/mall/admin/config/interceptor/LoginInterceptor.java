package com.dj.mall.admin.config.interceptor;

import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.model.contant.AuthConstant;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chengf
 * @date 2020/6/10 17:26
 * 自定义登录拦截器
 */
@Component("loginInterceptor")
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取session中存入的用户
        UserDTO USER = (UserDTO) request.getSession().getAttribute(AuthConstant.SESSION_USER);
        if (StringUtils.isEmpty(USER)) {
            response.sendRedirect(request.getContextPath() + "/auth/user/toLogin");
            return false;
        }
        return true;
    }
}
