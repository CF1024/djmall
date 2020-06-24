package com.dj.mall.auth.mapper.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.auth.entity.resource.Resource;
import com.dj.mall.auth.entity.role.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/3 22:54
 * 角色mapper接口
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据角色ID查角色已关联资源
     * @param roleId 角色 ID
     * @return
     * @throws DataAccessException
     */
    List<Resource> findRoleResourceBuRoleId(@Param("roleId") Integer roleId) throws DataAccessException;
}
