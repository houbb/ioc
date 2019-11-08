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
    String getName();

    /**
     * 设置名称
     * @param name 名称
     * @since 0.0.1
     */
    void setName(final String name);

    /**
     * 类名称
     * @return 类名称
     * @since 0.0.1
     */
    String getClassName();

    /**
     * 设置类名称
     * @param className 类名称
     * @since 0.0.1
     */
    void setClassName(final String className);

}
