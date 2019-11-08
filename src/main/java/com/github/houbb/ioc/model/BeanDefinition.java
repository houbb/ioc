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

    /**
     * 获取生命周期
     * @return 获取生命周期
     * @since 0.0.3
     */
    String getScope();

    /**
     * 设置是否单例
     * @param scope 是否单例
     * @since 0.0.3
     */
    void setScope(final String scope);

    /**
     * 是否为延迟加载
     * @return 是否
     * @since 0.0.3
     */
    boolean isLazyInit();

    /**
     * 设置是否为延迟加载
     * @param isLazyInit 是否为延迟加载
     * @since 0.0.3
     */
    void setLazyInit(final boolean isLazyInit);

}
