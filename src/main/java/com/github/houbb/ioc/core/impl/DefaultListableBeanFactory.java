package com.github.houbb.ioc.core.impl;

import com.github.houbb.ioc.core.ListableBeanFactory;

import java.util.List;

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

}
