package com.github.houbb.ioc.annotation;

import java.lang.annotation.*;

/**
 * 属性值指定
 * @author binbin.hou
 * @since 0.1.10
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Value {

    /**
     * 属性值指定
     * （1）如果是 ${} 则进行占位符处理，如果不是则保持原样。
     * @return 指定依赖名称
     * @since 0.1.10
     */
    String value();

}
