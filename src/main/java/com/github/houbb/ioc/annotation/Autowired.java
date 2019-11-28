package com.github.houbb.ioc.annotation;

import java.lang.annotation.*;

/**
 * 用于指定自动注入的方式
 * （1）放在普通字段直接返回单个结果
 * （2）如果放在集合上，则考虑将所有的接口对应的对象全部获取到。
 *
 * 为了便于后期维护，暂时不支持数组。
 *
 * @author binbin.hou
 * @since 0.1.6
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Autowired {

    /**
     * 指定依赖名称
     * @return 指定依赖名称
     * @since 0.1.6
     */
    String value() default "";

}
