package com.github.houbb.ioc.annotation;

import java.lang.annotation.*;

/**
 * 用于指定工厂方法的注解
 * @author binbin.hou
 * @since 0.0.6
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FactoryMethod {
}
