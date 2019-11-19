package com.github.houbb.ioc.annotation;

import java.lang.annotation.*;

/**
 * 用于指定 java 配置注解
 * @author binbin.hou
 * @since 0.1.1
 * @see com.github.houbb.ioc.support.name.BeanNameStrategy 命名策略
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface Configuration {

    /**
     * 组件名称
     * @return 组件名称
     * @since 0.1.1
     */
    String value() default "";

}
