package com.github.houbb.ioc.core.impl;

import com.github.houbb.heaven.support.handler.IHandler;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.SetUtil;
import com.github.houbb.ioc.annotation.Primary;
import com.github.houbb.ioc.core.ListableBeanFactory;
import com.github.houbb.ioc.exception.IocRuntimeException;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.support.aware.service.AwareService;
import com.github.houbb.ioc.support.aware.service.impl.DefaultAwareService;

import java.util.List;
import java.util.Set;

/**
 * listable bean 工厂接口
 * @author binbin.hou
 * @since 0.0.2
 */
public class DefaultListableBeanFactory extends DefaultBeanFactory implements ListableBeanFactory {

    /**
     * 获取 beans 信息列表
     *
     * @param requiredType 指定类型
     * @param <T>          泛型
     * @return 结果列表
     * @since 0.0.8
     * @throws IocRuntimeException 如果没有发现对应类
     */
    @Override
    public <T> List<T> getBeans(final Class<T> requiredType) {
        Set<String> beanNames = beanDefinitionRegistry.getBeanNames(requiredType);
        if (CollectionUtil.isEmpty(beanNames)) {
            return Guavas.newArrayList();
        }

        // 构建结果
        return CollectionUtil.toList(beanNames, new IHandler<String, T>() {
            @Override
            public T handle(String name) {
                return getBean(name, requiredType);
            }
        });
    }

    /**
     * 根据类型获取对应的属性名称
     *
     * @param requiredType 需求类型
     * @return bean 名称列表
     * @since 0.0.2 初始化
     * @since 0.1.5 设置为公开方法
     */
    @Override
    public Set<String> getBeanNames(Class requiredType) {
        return beanDefinitionRegistry.getBeanNames(requiredType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getRequiredTypeBean(Class<T> requiredType, String beanName) {
        Set<String> beanNames = this.getBeanNames(requiredType);
        if (CollectionUtil.isEmpty(beanNames)) {
            throw new IocRuntimeException("RequiredType of " + requiredType.getName() + " beans not found!");
        }
        if (beanNames.size() == 1) {
            final String firstBeanName = SetUtil.getFirst(beanNames);
            return (T) getBean(firstBeanName);
        }
        if (StringUtil.isNotEmpty(beanName)) {
            return (T) getBean(beanName);
        }

        //1. 获取 @Primary 对应的信息，不为空则返回。
        T primary = getPrimaryBean(requiredType);
        if(ObjectUtil.isNotNull(primary)) {
            return primary;
        }

        throw new IocRuntimeException("RequiredType of " + requiredType.getName() + " must be unique!");
    }

    /**
     * 获取指定了 {@link com.github.houbb.ioc.annotation.Primary} 优先的对象
     * （1）优先返回第一个该注解指定的对象
     * （2）如果没有，则返回空。
     *
     * @param requiredType 需求类型
     * @param <T> 泛型
     * @return 结果
     * @since 0.1.7
     */
    protected <T> T getPrimaryBean(Class<T> requiredType) {
        List<T> beans = this.getBeans(requiredType);
        if(CollectionUtil.isEmpty(beans)) {
            return null;
        }

        for(T bean : beans) {
            Class clazz = bean.getClass();
            if(clazz.isAnnotationPresent(Primary.class)) {
                return bean;
            }
        }
        return null;
    }

}
