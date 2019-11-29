package com.github.houbb.ioc.support.aware.service;

import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.core.ListableBeanFactory;

/**
 * @author binbin.hou
 * @since 0.1.8
 */
public interface AwareService {

    /**
     * 设置 Bean 工厂
     * @param beanFactory Bean 工厂
     * @return this
     * @since 0.1.8
     */
    AwareService setBeanFactory(final ListableBeanFactory beanFactory);

    /**
     * 通知所有对应的监听
     * @param beanName Bean 名称
     * @return this
     * @since 0.1.8
     */
    AwareService notifyAllAware(final String beanName);

}
