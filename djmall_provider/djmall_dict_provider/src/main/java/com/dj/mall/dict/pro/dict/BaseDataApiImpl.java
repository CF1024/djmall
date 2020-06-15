package com.dj.mall.dict.pro.dict;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.dict.api.dict.BaseDataApi;
import com.dj.mall.dict.dto.dict.BaseDataDTO;
import com.dj.mall.dict.entity.dict.BaseDataEntity;
import com.dj.mall.dict.mapper.dict.BaseDataMapper;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.contant.DictConstant;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author chengf
 * @date 2020/6/15 15:26
 * 字典数据
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BaseDataApiImpl extends ServiceImpl<BaseDataMapper, BaseDataEntity> implements BaseDataApi {
    /**
     * 查询全部 分页
     * @param baseDataDTO
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public PageResult findAll(BaseDataDTO baseDataDTO) throws Exception, BusinessException {
        IPage<BaseDataEntity> iPage = getBaseMapper().selectPage(new Page<>(baseDataDTO.getPageNo(), baseDataDTO.getPageSize()), new QueryWrapper<BaseDataEntity>());
        return new PageResult().toBuilder().pages(iPage.getPages()).list(DozerUtil.mapList(iPage.getRecords(), BaseDataDTO.class)).build();
    }

    /**
     * 根据父级名称查基础数据
     * @param parentCode
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public List<BaseDataDTO> findBaseDataByParentCode(String parentCode) throws Exception, BusinessException {
        return DozerUtil.mapList(getBaseMapper().selectList(new QueryWrapper<BaseDataEntity>().eq("parent_code", parentCode)), BaseDataDTO.class);
    }

    /**
     * 去重
     * @param baseDataDTO
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public Boolean deDuplicate(BaseDataDTO baseDataDTO) throws Exception, BusinessException {
        QueryWrapper<BaseDataEntity> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(baseDataDTO.getParentCode())) {
            queryWrapper.ne("parent_code", baseDataDTO.getParentCode());
        }
        if (!StringUtils.isEmpty(baseDataDTO.getBaseName())) {
            queryWrapper.eq("base_name", baseDataDTO.getBaseName());
        }
        if (!StringUtils.isEmpty(baseDataDTO.getBaseCode())) {
            queryWrapper.eq("base_code", baseDataDTO.getBaseCode());
        }
        return getBaseMapper().selectOne(queryWrapper) == null;
    }

    /**
     * 新增
     *
     * @param baseDataDTO
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void addBase(BaseDataDTO baseDataDTO) throws Exception, BusinessException {
        getBaseMapper().insert(DozerUtil.map(baseDataDTO.toBuilder().baseCode(baseDataDTO.getBaseCode().toUpperCase()).build(), BaseDataEntity.class));
    }

    /**
     * 根据基础编码去查基础数据
     * @param baseCode 基础编码
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public BaseDataDTO findBaseDataByBaseCode(String baseCode) throws Exception, BusinessException {
        return DozerUtil.map(getBaseMapper().selectOne(new QueryWrapper<BaseDataEntity>().eq("base_code", baseCode)), BaseDataDTO.class);
    }

    /**
     * 修改
     * @param baseDataDTO
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void updateBase(BaseDataDTO baseDataDTO) throws Exception, BusinessException {
        getBaseMapper().update(DozerUtil.map(baseDataDTO, BaseDataEntity.class), new QueryWrapper<BaseDataEntity>().eq("base_code", baseDataDTO.getBaseCode()));
    }
}
