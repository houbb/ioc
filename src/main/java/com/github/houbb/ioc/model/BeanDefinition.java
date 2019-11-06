package com.github.houbb.ioc.model;

/**
 * 对象定义属性
 * @author binbin.hou
 * @since 0.0.1
 */
public interface BeanDefinition {

    /**
     * 名称
     * @return 名称
     * @since 0.0.1
     */
    String name();

    /**
     * 设置名称
     * @param name 名称
     * @return this
     * @since 0.0.1
     */
    BeanDefinition name(final String name);

    /**
     * 类名称
     * @return 类名称
     */
    String className();

    /**
     * 设置类名称
     * @param className 类名称
     * @return this
     * @since 0.0.1
     */
    BeanDefinition className(final String className);

}
