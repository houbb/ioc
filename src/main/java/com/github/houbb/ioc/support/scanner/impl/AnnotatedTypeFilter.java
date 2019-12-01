package com.github.houbb.ioc.support.scanner.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.reflect.meta.annotation.IAnnotationTypeMeta;
import com.github.houbb.ioc.support.scanner.TypeFilter;

import java.util.List;

/**
 * <p> project: ioc-AnnotatedTypeFilter </p>
 * <p> create on 2019/12/1 12:52 </p>
 *
 * @author Administrator
 * @since 0.1.11
 */
@ThreadSafe
public class AnnotatedTypeFilter implements TypeFilter {

    @Override
    public boolean matches(Class clazz, IAnnotationTypeMeta typeMeta, List<Class> classList) {
        return typeMeta.isAnnotatedOrRef(classList);
    }

}
