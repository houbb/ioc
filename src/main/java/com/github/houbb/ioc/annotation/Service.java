package com.github.houbb.ioc.annotation;

import java.lang.annotation.*;

/**
 * 用于指定 ioc 管理类属性
 * @author binbin.hou
 * @since 0.1.11
 * @see com.github.houbb.ioc.support.name.BeanNameStrategy 命名策略
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface Service {

    /**
     * 服务类名称
     * @return 服务类名称
     * @since 0.1.11
     */
    String value() default "";

}
