package com.github.houbb.ioc.model.impl;

import com.github.houbb.ioc.model.BeanDefinition;

/**
 * 默认对象定义属性
 * @author binbin.hou
 * @since 0.0.1
 */
public class DefaultBeanDefinition implements BeanDefinition {

    /**
     * 名称
     * @since 0.0.1
     */
    private String name;

    /**
     * 类名称
     * @since 0.0.1
     */
    private String className;

    /**
     * 生命周期
     * @since 0.0.3
     */
    private String scope;

    /**
     * 是否为延迟加载
     * @since 0.0.3
     */
    private boolean lazyInit;

    /**
     * 初始化方法信息
     * @since 0.0.4
     */
    private String initialize;

    /**
     * 销毁方法
     * @since 0.0.4
     */
    private String destroy;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String getScope() {
        return scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public boolean isLazyInit() {
        return lazyInit;
    }

    @Override
    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    @Override
    public String getInitialize() {
        return initialize;
    }

    @Override
    public void setInitialize(String initialize) {
        this.initialize = initialize;
    }

    @Override
    public String getDestroy() {
        return destroy;
    }

    @Override
    public void setDestroy(String destroy) {
        this.destroy = destroy;
    }
}
