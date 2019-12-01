package com.github.houbb.ioc.support.scanner;

import com.github.houbb.heaven.reflect.meta.annotation.IAnnotationTypeMeta;

import java.util.List;

/**
 * <p> project: ioc-TypeIncludeFilter </p>
 * <p> create on 2019/12/1 11:18 </p>
 *
 * @author Administrator
 * @since 0.1.11
 */
public interface TypeFilter {

    /**
     * 是否匹配
     * @param clazz 类信息
     * @param typeMeta 注解元数据信息
     * @param classList 类类表
     * @return 结果
     * @since 0.1.11
     */
    boolean matches(final Class clazz, final IAnnotationTypeMeta typeMeta, final List<Class> classList);

}