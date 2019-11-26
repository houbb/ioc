package com.github.houbb.ioc.support.annotation;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.ioc.annotation.Scope;
import com.github.houbb.ioc.constant.ScopeConst;

import java.lang.reflect.Method;

/**
 * <p> project: ioc-Scopes </p>
 * <p> create on 2019/11/26 22:25 </p>
 *
 * @author Administrator
 * @since 0.1.3
 */
public final class Scopes {

    private Scopes(){}

    /**
     * 获取指定类的 scope 信息
     * 后期可以支持自定义注解，包含元注解 {@link com.github.houbb.ioc.annotation.Scope} 即可。
     * @param clazz 类型
     * @return scope 信息
     * @since 0.1.3
     */
    public static String getScope(final Class clazz) {
        ArgUtil.notNull(clazz, "clazz");

        if(clazz.isAnnotationPresent(Scope.class)) {
            Scope scope = (Scope) clazz.getAnnotation(Scope.class);
            return scope.value();
        }
        return ScopeConst.SINGLETON;
    }

    /**
     * 获取指定方法的 scope 信息
     * 后期可以支持自定义注解，包含元注解 {@link com.github.houbb.ioc.annotation.Scope} 即可。
     * @param method 方法信息
     * @return scope 信息
     * @since 0.1.3
     */
    public static String getScope(final Method method) {
        ArgUtil.notNull(method, "method");

        if(method.isAnnotationPresent(Scope.class)) {
            Scope scope = (Scope) method.getAnnotation(Scope.class);
            return scope.value();
        }
        return ScopeConst.SINGLETON;
    }

}
