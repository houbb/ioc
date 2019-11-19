package com.github.houbb.ioc.annotation;

import java.lang.annotation.*;

/**
 * 用于指定 Bean 信息
 * @author binbin.hou
 * @since 0.1.2
 * @see com.github.houbb.ioc.support.name.BeanNameStrategy 命名策略
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Bean {

    /**
     * 组件名称
     * @return 组件名称
     * @since 0.1.2
     */
    String value() default "";

    /**
     * 初始化方法
     * @return 初始化方法
     * @since 0.1.2
     */
    String initMethod() default "";

    /**
     * 销毁方法
     * @return 销毁方法
     * @since 0.1.2
     */
    String destroyMethod() default "";

}
