package com.github.houbb.ioc.annotation;

import java.lang.annotation.*;

/**
 * 注定优先级为最优先
 * @author binbin.hou
 * @since 0.1.7
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Primary {
}