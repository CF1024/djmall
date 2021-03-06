/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_model
 * 类名：DozerUtil
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.model.util;

import org.dozer.DozerBeanMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author chengf
 * @date 2020/6/3 17:59
 * 领域对象模型工具类
 */
public class DozerUtil {

    public static final DozerBeanMapper dozer = new DozerBeanMapper();
    static {
        dozer.setMappingFiles(Collections.singletonList("dozerJdk8Converters.xml"));
    }
    /**
     * 对象映射（源与目标属性不一致的情况，可在目标属性中通过@Mapping 手动映射）
     *
     * @param source 源
     * @param target 目标
     * @param <T>
     * @return
     */
    public static <T> T map(Object source, Class<T> target) {
        if (null == source) {
            return null;
        }
        return dozer.map(source, target);
    }


    /**
     * List映射
     *
     * @param <T>
     * @param sourceList 源List
     * @param target     目标
     * @return
     */
    public static <T> List<T> mapList(List<?> sourceList, Class<T> target) {
        if (sourceList == null) {
            return null;
        }
        return sourceList.stream().map(source -> map(source, target)).collect(Collectors.toList());
    }

}

