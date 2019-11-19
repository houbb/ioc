package com.github.houbb.ioc.annotation;

import java.lang.annotation.*;

/**
 * 用于指定 ioc 管理类属性
 * @author binbin.hou
 * @since 0.1.1
 * @see com.github.houbb.ioc.support.name.BeanNameStrategy 命名策略
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface Component {

    /**
     * 组件名称
     * @return 组件名称
     * @since 0.1.1
     */
    String value() default "";

}
