package com.github.houbb.ioc.annotation;

import com.github.houbb.ioc.constant.ProfileConst;
import com.github.houbb.ioc.support.condition.impl.ProfileCondition;

import java.lang.annotation.*;

/**
 * 指定环境信息
 *
 * （1）可以指定在配置类上 {@link Configuration} 所有配置类即后期的 {@link Component} 组件类。
 * （2）可以放在配置类中 {@link Bean} 标注的方法中。
 *
 * 如果方法没有指定，则默认去当前类的环境信息。
 *
 * @author binbin.hou
 * @since 0.1.9
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Conditional(ProfileCondition.class)
public @interface Profile {

    /**
     * 指定环境信息
     * @return 指定环境信息
     * @see ProfileConst 环境常量
     * @since 0.1.9
     */
    String[] value();

}
