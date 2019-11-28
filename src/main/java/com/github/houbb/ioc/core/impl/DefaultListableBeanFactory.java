package com.github.houbb.ioc.core.impl;

import com.github.houbb.ioc.core.ListableBeanFactory;

import java.util.List;
import java.util.Set;

/**
 * listable bean 工厂接口
 * @author binbin.hou
 * @since 0.0.2
 */
public class DefaultListableBeanFactory extends DefaultBeanFactory implements ListableBeanFactory {

    @Override
    public <T> List<T> getBeans(final Class<T> requiredType) {
        return super.getBeans(requiredType);
    }

    @Override
    public Set<String> getBeanNames(Class requiredType) {
        return super.getBeanNames(requiredType);
    }

    @Override
    public <T> T getRequiredTypeBean(Class<T> requiredType, String beanName) {
        return super.getRequiredTypeBean(requiredType, beanName);
    }

}
