package com.github.houbb.ioc.annotation;

import java.lang.annotation.*;

/**
 * 用于指生命周期 scope
 * @author binbin.hou
 * @since 0.1.3
 * @see com.github.houbb.ioc.constant.enums.ScopeEnum 枚举
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Scope {

    /**
     * 生命周期指定
     * @return 生命周期指定
     * @since 0.1.3
     */
    String value() default "singleton";

}
