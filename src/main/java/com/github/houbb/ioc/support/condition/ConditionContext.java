package com.github.houbb.ioc.support.condition;

import com.github.houbb.ioc.core.BeanFactory;

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

}
