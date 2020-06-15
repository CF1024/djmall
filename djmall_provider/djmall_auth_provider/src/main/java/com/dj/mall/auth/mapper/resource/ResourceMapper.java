package com.dj.mall.auth.mapper.resource;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.auth.entity.resource.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/3 22:55
 *资源mapper接口
 */
public interface ResourceMapper extends BaseMapper<Resource> {
    /**
     * 级联删除
     * @param ids
     * @param isDel
     * @throws DataAccessException
     */
    void updateResourceIsDelByIds(@Param("ids") List<Integer> ids, @Param("isDel") String isDel) throws DataAccessException;
}
