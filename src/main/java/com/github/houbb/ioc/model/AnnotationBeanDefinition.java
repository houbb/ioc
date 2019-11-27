package com.github.houbb.ioc.model;

import java.util.List;

/**
 * 注解对象定义属性
 * @author binbin.hou
 * @since 0.1.2
 */
public interface AnnotationBeanDefinition extends BeanDefinition {

    /**
     * 设置配置对象名称
     * @param configurationName 配置对象名称
     * @since 0.1.2
     */
    void setConfigurationName(final String configurationName);

    /**
     * 获取配置名称
     * @return 配置名称
     * @since 0.1.2
     */
    String getConfigurationName();

    /**
     * 设置新建实例方法名称
     * @param configurationBeanMethod 方法名称
     * @since 0.1.2
     * @see java.lang.reflect.Method#invoke(Object, Object...) 反射创建实例
     */
    void setConfigurationBeanMethod(final String configurationBeanMethod);

    /**
     * 获取新建实例方法名称
     * @return 方法名称
     * @since 0.1.2
     */
    String getConfigurationBeanMethod();

    /**
     * 设置对象方法参数类型列表
     * @param classes 参数类型列表
     * @since 0.1.5
     */
    void setConfigBeanMethodParamTypes(final Class[] classes);

    /**
     * 获取对象方法参数类型列表
     * @since 0.1.5
     */
    Class[] getConfigBeanMethodParamTypes();

    /**
     * 设置参数依赖的对象列表
     * @param refs 依赖对象
     * @since 0.1.5
     */
    void setConfigBeanMethodParamRefs(final List<String> refs);

    /**
     * 获取参数依赖的对象列表
     * @since 0.1.5
     */
    List<String> getConfigBeanMethodParamRefs();

}
