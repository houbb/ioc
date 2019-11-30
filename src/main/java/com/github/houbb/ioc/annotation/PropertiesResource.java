package com.github.houbb.ioc.annotation;

import java.lang.annotation.*;

/**
 * 用于指定配置文件路径
 * @author binbin.hou
 * @since 0.1.10
 * @see Value 值注解
 * @see Configuration 必须和配置注解共同使用才会使用（保证规范性）
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface PropertiesResource {

    /**
     * 配置文件路径列表
     * @return 指定依赖名称
     * @since 0.1.10
     */
    String[] value();

}
