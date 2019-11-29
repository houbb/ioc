package com.github.houbb.ioc.support.condition;

import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.support.lifecycle.registry.BeanDefinitionRegistry;

/**
 * 条件上下文执行接口
 * @author binbin.hou
 * @since 0.1.8
 */
public interface ConditionContext {

    /**
     * 获取对象工厂信息
     * @return 工厂信息
     * @since 0.1.8
     */
    BeanFactory getBeanFactory();

    /**
     * 获取对象信息注册类
     * @return 对象信息注册类
     * @since 0.1.8
     */
    BeanDefinitionRegistry getBeanDefinitionRegistry();

}
