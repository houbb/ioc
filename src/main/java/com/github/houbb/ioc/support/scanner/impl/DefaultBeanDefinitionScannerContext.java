package com.github.houbb.ioc.support.scanner.impl;

import com.github.houbb.ioc.support.name.BeanNameStrategy;
import com.github.houbb.ioc.support.scanner.BeanDefinitionScannerContext;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * <p> project: ioc-DefaultBeanDefinitionScannerContext </p>
 * <p> create on 2019/12/1 12:48 </p>
 *
 * @author Administrator
 * @since 0.1.11
 */
public class DefaultBeanDefinitionScannerContext implements BeanDefinitionScannerContext {

    /**
     * 获取需要扫描的包列表
     * @since 0.1.11
     */
    private List<String> scanPackages;

    /**
     * 获取所有需要排除的类列表
     * @since 0.1.11
     */
    private List<Class> excludes;

    /**
     * 获取所有需要包含的类列表
     * @since 0.1.11
     */
    private List<Class> includes;

    /**
     * 获取对象名称策略
     * @since 0.1.11
     */
    private Class<? extends BeanNameStrategy> beanNameStrategy;

    @Override
    public List<String> getScanPackages() {
        return scanPackages;
    }

    public void setScanPackages(List<String> scanPackages) {
        this.scanPackages = scanPackages;
    }

    @Override
    public List<Class> getExcludes() {
        return excludes;
    }

    public void setExcludes(List<Class> excludes) {
        this.excludes = excludes;
    }

    @Override
    public List<Class> getIncludes() {
        return includes;
    }

    public void setIncludes(List<Class> includes) {
        this.includes = includes;
    }

    @Override
    public Class<? extends BeanNameStrategy> getBeanNameStrategy() {
        return beanNameStrategy;
    }

    public void setBeanNameStrategy(Class<? extends BeanNameStrategy> beanNameStrategy) {
        this.beanNameStrategy = beanNameStrategy;
    }
}
