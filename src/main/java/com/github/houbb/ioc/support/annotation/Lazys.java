package com.github.houbb.ioc.support.annotation;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.ioc.annotation.Lazy;
import com.github.houbb.ioc.annotation.Scope;

import java.lang.reflect.Method;

/**
 * <p> project: ioc-Scopes </p>
 * <p> create on 2019/11/26 22:25 </p>
 *
 * @author Administrator
 * @since 0.1.3
 */
public final class Lazys {

    private Lazys(){}

    /**
     * 获取指定类的 Lazy 信息
     * 后期可以支持自定义注解，包含元注解 {@link com.github.houbb.ioc.annotation.Lazy} 即可。
     * @param clazz 类型
     * @return Lazy 信息
     * @since 0.1.3
     */
    public static boolean getLazy(final Class clazz) {
        ArgUtil.notNull(clazz, "clazz");

        if(clazz.isAnnotationPresent(Lazy.class)) {
            Lazy lazy = (Lazy) clazz.getAnnotation(Lazy.class);
            return lazy.value();
        }
        return false;
    }

    /**
     * 获取指定方法的 scope 信息
     * 后期可以支持自定义注解，包含元注解 {@link com.github.houbb.ioc.annotation.Lazy} 即可。
     *
     * @param method 方法信息
     * @return scope 信息
     * @since 0.1.3
     */
    public static boolean getLazy(final Method method) {
        ArgUtil.notNull(method, "method");

        if(method.isAnnotationPresent(Lazy.class)) {
            Lazy lazy = (Lazy) method.getAnnotation(Lazy.class);
            return lazy.value();
        }
        return false;
    }

}
