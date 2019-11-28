package com.github.houbb.ioc.support.aware;

import com.github.houbb.ioc.core.BeanFactory;

/**
 * {@link com.github.houbb.ioc.core.BeanFactory} bean 工厂监听器
 * @author binbin.hou
 * @since 0.1.6
 */
public interface BeanFactoryAware extends Aware {

    /**
     * 设置 BeanFactory
     * @param beanFactory 对象工厂
     * @since 0.1.6
     */
    void setBeanFactory(BeanFactory beanFactory);

}
