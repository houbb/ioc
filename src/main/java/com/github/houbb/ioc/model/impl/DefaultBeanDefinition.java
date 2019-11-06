package com.github.houbb.ioc.model.impl;

import com.github.houbb.ioc.model.BeanDefinition;

/**
 * 对象定义属性
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
     */
    private String className;

    @Override
    public String name() {
        return name;
    }

    @Override
    public DefaultBeanDefinition name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String className() {
        return className;
    }

    @Override
    public DefaultBeanDefinition className(String className) {
        this.className = className;
        return this;
    }

    @Override
    public String toString() {
        return "DefaultBeanDefinition{" +
                "name='" + name + '\'' +
                ", className='" + className + '\'' +
                '}';
    }

}
