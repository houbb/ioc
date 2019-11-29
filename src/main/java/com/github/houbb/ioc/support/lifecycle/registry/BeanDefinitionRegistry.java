package com.github.houbb.ioc.support.lifecycle.registry;

import com.github.houbb.ioc.model.BeanDefinition;

import java.util.List;
import java.util.Set;

/**
 * 对象注册
 *
 * （1）该类的职责是负责对象的创建，不关心对应的 bean 的获取
 * 某种角度而言，这个类对于用户可以不那么可见。
 *
 * （2）{@link com.github.houbb.ioc.core.BeanFactory} 更加关心对象的获取。
 *
 * @author binbin.hou
 * @since 0.1.8
 */
public interface BeanDefinitionRegistry {

    /**
     * Register a new bean definition with this registry.
     * Must support RootBeanDefinition and ChildBeanDefinition.
     * @param beanName the name of the bean instance to register
     * @param beanDefinition definition of the bean instance to register
     * or if there is already a BeanDefinition for the specified bean name
     * (and we are not allowed to override it)
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * Remove the BeanDefinition for the given name.
     * @param beanName the name of the bean instance to register
     */
    void removeBeanDefinition(String beanName);

    /**
     * Return the BeanDefinition for the given bean name.
     * @param beanName name of the bean to find a definition for
     * @return the BeanDefinition for the given name (never {@code null})
     */
    BeanDefinition getBeanDefinition(String beanName);

    /**
     * Check if this registry contains a bean definition with the given name.
     * @param beanName the name of the bean to look for
     * @return if this registry contains a bean definition with the given name
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * Return the names of all beans defined in this registry.
     * @return the names of all beans defined in this registry,
     * or an empty array if none defined
     */
    List<String> getBeanDefinitionNames();

    /**
     * Return the number of beans defined in the registry.
     * @return the number of beans defined in the registry
     */
    int getBeanDefinitionCount();

    /**
     * 根据类型获取对应的属性名称
     *
     * @param requiredType 需求类型
     * @return bean 名称列表
     * @since 0.0.2 初始化
     * @since 0.1.5 设置为公开方法
     */
    Set<String> getBeanNames(final Class requiredType);

}
