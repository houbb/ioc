package com.github.houbb.ioc.annotation;

import com.github.houbb.ioc.support.condition.Condition;

import java.lang.annotation.*;

/**
 * 标识一个对象是否被初始化。
 *
 * （1）如果不符合条件，直接跳过该对象的所有信息及其注册。
 * （2）如果这个注解放在 {@link Configuration} 对应的类上，则该类及其所有子类都符合这个属性。
 * （3）如果这个注解放在方法上，则只有该方法才被赋予这个属性。
 * 方法级别的优先级高于类级别。
 *
 * @author binbin.hou
 * @since 0.1.8
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Conditional {

    /**
     * 条件上下文对应的接口
     * @return 条件列表
     * @since 0.1.8
     */
    Class<? extends Condition> value();

}
