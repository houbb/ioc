package com.github.houbb.ioc.core.impl;

import com.github.houbb.heaven.support.handler.IHandler;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.ioc.core.ListableBeanFactory;
import com.github.houbb.ioc.exception.IocRuntimeException;

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
        ArgUtil.notNull(requiredType, "requiredType");

        Set<String> beanNames = super.getBeanNames(requiredType);
        if(CollectionUtil.isEmpty(beanNames)) {
            throw new IocRuntimeException(requiredType + " bean names is empty!");
        }

        // 构建结果
        return CollectionUtil.toList(beanNames, new IHandler<String, T>() {
            @Override
            public T handle(String name) {
                return DefaultListableBeanFactory.super.getBean(name, requiredType);
            }
        });
    }

}
