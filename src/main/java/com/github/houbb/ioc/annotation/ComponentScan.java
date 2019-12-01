package com.github.houbb.ioc.annotation;

import com.github.houbb.ioc.support.name.BeanNameStrategy;
import com.github.houbb.ioc.support.name.impl.DefaultBeanNameStrategy;

import java.lang.annotation.*;

/**
 * 用于扫描组件类
 *
 * @author binbin.hou
 * @since 0.1.11
 * @see com.github.houbb.ioc.support.name.BeanNameStrategy 命名策略
 * @see Component 组件注解
 * @see com.github.houbb.ioc.support.scanner.TypeFilter 类型过滤类
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ComponentScan {

    /**
     * 指定扫描包名称
     * @return 组件名称
     * @since 0.1.11
     */
    String[] value();

    /**
     * 排除的注解
     * @return 注解类列表
     * @since 0.1.11
     */
    Class<? extends Annotation>[] excludes() default {};

    /**
     * 包含的注解
     * @return 注解类列表
     * @since 0.1.11
     */
    Class<? extends Annotation>[] includes() default {Component.class};

    /**
     * 对象名称策略
     * @return 名称策略类
     * @since 0.1.11
     */
    Class<? extends BeanNameStrategy> beanNameStrategy() default DefaultBeanNameStrategy.class;

}
