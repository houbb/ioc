package com.github.houbb.ioc.util;

import com.github.houbb.heaven.annotation.CommonEager;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.Optional;
import com.github.houbb.ioc.exception.IocRuntimeException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * <p> project: ioc-ClassUtils </p>
 * <p> create on 2019/11/6 19:43 </p>
 *
 * @author Administrator
 * @since 0.0.1
 */
@CommonEager
public final class ClassUtils {

    /**
     * 获取当前的 class loader
     * @return 当前的 class loader
     * @since 0.0.1
     */
    public static ClassLoader currentClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 获取类信息
     * @since 0.0.1
     */
    public static Class getClass(final String className) {
        ArgUtil.notEmpty(className, "className");

        try {
            return currentClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new IocRuntimeException(e);
        }
    }

    /**
     * 直接根据 class 无参构造器创建实例
     * @param clazz 类信息
     * @return 实例
     * @since 0.0.1
     */
    public static Object newInstance(final Class clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IocRuntimeException(e);
        }
    }

    /**
     * 获取指定注解的方法
     * @param tClass 雷西悉尼
     * @param annotationClass 注解信息
     * @return 方法的 optional 信息
     * @since 0.0.4
     */
    public static Optional<Method> getMethodOptional(final Class tClass, final Class<? extends Annotation> annotationClass) {
        final Method[] methods = tClass.getMethods();

        if(ArrayUtil.isEmpty(methods)) {
            return Optional.empty();
        }
        for(Method method : methods) {
            if(method.isAnnotationPresent(annotationClass)) {
                return Optional.of(method);
            }
        }
        return Optional.empty();
    }

}
