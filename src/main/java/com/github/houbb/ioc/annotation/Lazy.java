package com.github.houbb.ioc.annotation;

import java.lang.annotation.*;

/**
 * 是否延迟加载
 * @author binbin.hou
 * @since 0.1.3
 * @see com.github.houbb.ioc.support.name.BeanNameStrategy 命名策略
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Lazy {

    /**
     * 是否延迟加载
     * @return 是否延迟加载
     * @since 0.1.3
     */
    boolean value() default false;

}
