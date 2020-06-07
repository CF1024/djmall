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
 * @date 2020/6/6 13:19
 * 自定义拦截器
 */
@Component //表示该Bean的一个组件
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取session中存入的用户
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute(AuthConstant.SESSION_USER);
        //判断如果是空则进行拦截
        if (StringUtils.isEmpty(userDTO)) {
            response.sendRedirect(request.getContextPath() + "/auth/user/toLogin");
            return false;
        }
        return true;
    }
}
