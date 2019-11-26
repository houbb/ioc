package com.github.houbb.ioc.model;

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

}
