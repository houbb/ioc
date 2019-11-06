package com.github.houbb.ioc.core.impl;

import com.github.houbb.ioc.core.BeanFactory;

/**
 * bean 工厂接口
 * @author binbin.hou
 * @since 0.0.1
 */
public class DefaultBeanFactory implements BeanFactory {

    @Override
    public Object getBean(String beanName) {
        return null;
    }

    @Override
    public <T> T getBean(String beanName, Class<T> tClass) {
        return null;
    }

}
