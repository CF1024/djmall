package com.dj.mall.admin.config.interceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.auth.api.user.UserApi;
import com.dj.mall.auth.dto.resource.ResourceDTO;
import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.model.contant.AuthConstant;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author chengf
 * @date 2020/6/6 13:19
 * 自定义权限拦截器
 */
@Component("authInterceptor") //表示该Bean的一个组件
public class AuthInterceptor implements HandlerInterceptor {
    @Reference
    private UserApi userApi;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //上下文路径
        String ctx = request.getContextPath();
        //访问的路径合法性
        String url = request.getRequestURI();
        //获取session中存入的用户
        UserDTO USER = (UserDTO) request.getSession().getAttribute(AuthConstant.SESSION_USER);
        //获取用户关联的资源
        List<ResourceDTO> resourceDTOList = USER.getPermissionList();

        for (ResourceDTO resource : resourceDTOList) {
            if (url.equals(ctx + resource.getUrl())) {
                return true;
            }
        }
        //判断如果是空则进行拦截
        response.sendRedirect(ctx + "/index/toDisplay");
        return false;
    }
}
