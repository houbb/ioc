package com.github.houbb.ioc.support.condition.impl;

import com.github.houbb.heaven.reflect.meta.annotation.IAnnotationTypeMeta;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.support.condition.ConditionContext;
import com.github.houbb.ioc.support.lifecycle.registry.BeanDefinitionRegistry;

/**
 * 条件上下文执行接口
 * @author binbin.hou
 * @since 0.1.8
 */
public class DefaultConditionContext implements ConditionContext {

    /**
     * 获取对象工厂信息
     * @since 0.1.8
     */
    private BeanFactory beanFactory;

    /**
     * 获取对象信息注册类
     * @since 0.1.8
     */
    private BeanDefinitionRegistry beanDefinitionRegistry;

    /**
     * 获取注解相关元信息
     * @since 0.1.8
     */
    private IAnnotationTypeMeta annotationTypeMeta;

    @Override
    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public BeanDefinitionRegistry getBeanDefinitionRegistry() {
        return beanDefinitionRegistry;
    }

    public void setBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public IAnnotationTypeMeta getAnnotationTypeMeta() {
        return annotationTypeMeta;
    }

    public void setAnnotationTypeMeta(IAnnotationTypeMeta annotationTypeMeta) {
        this.annotationTypeMeta = annotationTypeMeta;
    }
}