package com.github.houbb.ioc.annotation;

import java.lang.annotation.*;

/**
 * 用于指定信息
 * （1）暂时不做实际处理，仅供查阅使用。
 * @author binbin.hou
 * @since 0.1.5
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Description {

    /**
     * 描述信息
     * @return 组件名称
     * @since 0.1.5
     */
    String value() default "";

}
