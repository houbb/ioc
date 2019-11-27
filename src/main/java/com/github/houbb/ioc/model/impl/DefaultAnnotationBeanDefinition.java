package com.github.houbb.ioc.model.impl;

import com.github.houbb.ioc.model.AnnotationBeanDefinition;

import java.util.List;

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

    /**
     * 配置对象方法参数类型
     * @since 0.1.5
     */
    private Class[] configBeanMethodParamTypes;

    /**
     * 配置对象方法参数引用
     * @since 0.1.5
     */
    private List<String> configBeanMethodParamRefs;

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

    @Override
    public Class[] getConfigBeanMethodParamTypes() {
        return configBeanMethodParamTypes;
    }

    @Override
    public void setConfigBeanMethodParamTypes(Class[] configBeanMethodParamTypes) {
        this.configBeanMethodParamTypes = configBeanMethodParamTypes;
    }

    @Override
    public List<String> getConfigBeanMethodParamRefs() {
        return configBeanMethodParamRefs;
    }

    @Override
    public void setConfigBeanMethodParamRefs(List<String> configBeanMethodParamRefs) {
        this.configBeanMethodParamRefs = configBeanMethodParamRefs;
    }
}

