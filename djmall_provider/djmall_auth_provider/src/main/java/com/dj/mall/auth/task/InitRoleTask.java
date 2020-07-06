/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_provider
 * 类名：InitRoleTask
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.auth.api.role.RoleApi;
import com.dj.mall.auth.dto.resource.ResourceDTO;
import com.dj.mall.auth.dto.role.RoleDTO;
import com.dj.mall.cmpt.RedisApi;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.contant.DictConstant;
import com.dj.mall.model.contant.RedisConstant;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/24 11:05
 * 初始化角色资源任务
 */
@Component
public class InitRoleTask {
    /**
     * 角色api
     */
    @Autowired
    private RoleApi roleApi;
    /**
     * redis的api
     */
    @Reference
    private RedisApi redisApi;

    /**
     * 初始化角色资源
     * @param applicationReadyEvent
     * @throws Exception
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initRoleResource(ApplicationReadyEvent applicationReadyEvent) throws Exception {
        System.out.println("init role resource create start");
        //获取全部角色
        PageResult pageResult = roleApi.findAll(DozerUtil.map(RoleDTO.class, RoleDTO.class));
        List<RoleDTO> roleList = (List<RoleDTO>) pageResult.getList();
        for (RoleDTO role : roleList) {
            //角色已关联资源
            List<ResourceDTO> resourceList = roleApi.findRoleResourceBuRoleId(role.getRoleId());
            redisApi.pushHash(RedisConstant.ROLE_ALL_KEY, RedisConstant.ROLE_ID_KEY + role.getRoleId(), resourceList);
        }
        System.out.println("init role resource create end");
    }
}
