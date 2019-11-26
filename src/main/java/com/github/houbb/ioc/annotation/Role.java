package com.github.houbb.ioc.annotation;

import java.lang.annotation.*;

/**
 * 用于指定角色的信息
 *
 * 暂时不做该注解的处理，仅供使用参考。
 * @author binbin.hou
 * @since 0.1.4
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Role {

    /**
     * 角色名称
     * @return 角色名称
     * @since 0.1.4
     */
    String value() default "";

}
