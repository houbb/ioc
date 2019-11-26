package com.github.houbb.ioc.model.impl;

import com.github.houbb.ioc.model.AnnotationBeanDefinition;

/**
 * 默认注解对象定义属性
 * @author binbin.hou
 * @since 0.1.2
 */
public class DefaultAnnotationBeanDefinition extends DefaultBeanDefinition implements AnnotationBeanDefinition {

    /**
     * 配置信息名称
     * @since 0.1.2
     */
    private String configurationName;

    /**
     * 配置信息对象名称
     * @since 0.1.2
     */
    private String configurationBeanMethod;

    @Override
    public String getConfigurationName() {
        return configurationName;
    }

    @Override
    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
    }

    @Override
    public String getConfigurationBeanMethod() {
        return configurationBeanMethod;
    }

    @Override
    public void setConfigurationBeanMethod(String configurationBeanMethod) {
        this.configurationBeanMethod = configurationBeanMethod;
    }

}

