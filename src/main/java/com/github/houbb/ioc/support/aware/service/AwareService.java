package com.github.houbb.ioc.support.aware.service;

import com.github.houbb.ioc.context.ApplicationContext;
import com.github.houbb.ioc.core.BeanFactory;

/**
 * @author binbin.hou
 * @since 0.1.8
 */
public interface AwareService {

    /**
     * 设置 Bean 工厂
     * @param beanFactory Bean 工厂
     * @since 0.1.8
     */
    void setBeanFactory(final BeanFactory beanFactory);

    /**
     * 通知所有 {@link com.github.houbb.ioc.support.aware.BeanNameAware} 对应的监听
     * @param beanName Bean 名称
     * @since 0.1.8
     */
    void notifyAllBeanNameAware(final String beanName);

    /**
     * 通知所有上下文监听器
     * @param applicationContext 上下文
     * @since 0.1.8
     */
    void notifyAllApplicationContextAware(final ApplicationContext applicationContext);

}
