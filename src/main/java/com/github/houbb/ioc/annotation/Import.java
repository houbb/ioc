package com.github.houbb.ioc.annotation;

import java.lang.annotation.*;

/**
 * 用于导入配置信息
 *
 * @author binbin.hou
 * @since 0.1.4
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Import {

    /**
     * 导入配置类信息
     * @return 配置类数组
     * @since 0.1.4
     */
    Class[] value();

}
