package com.github.houbb.ioc.model.impl;

import com.github.houbb.ioc.constant.enums.BeanSourceTypeEnum;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.model.ConstructorArgDefinition;
import com.github.houbb.ioc.model.PropertyArgDefinition;

import java.util.List;
import java.util.Objects;

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

    /**
     * 工厂类方法
     * @since 0.0.6
     */
    private String factoryMethod;

    /**
     * 构造器列表
     * @since 0.0.6
     */
    private List<ConstructorArgDefinition> constructorArgList;

    /**
     * 属性参数列表
     * @since 0.0.7
     */
    private List<PropertyArgDefinition> propertyArgList;

    /**
     * 是否为抽象类
     * （1）如果为抽象的时候，那么就不需要进行创建这个对象。
     * （2）这个对象更多的是提供属性，暂时不支持使用。
     * @since 0.0.9
     */
    private boolean abstractClass;

    /**
     * 父类名称
     * @since 0.0.9
     */
    private String parentName;

    /**
     * 对象数据来源
     * @since 0.1.2
     */
    private BeanSourceTypeEnum beanSourceType;

    /**
     * 新建对象实例
     * @return 对象实例
     * @since 0.1.1
     */
    public static DefaultBeanDefinition newInstance() {
        return new DefaultBeanDefinition();
    }

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

    @Override
    public String getFactoryMethod() {
        return factoryMethod;
    }

    @Override
    public void setFactoryMethod(String factoryMethod) {
        this.factoryMethod = factoryMethod;
    }

    @Override
    public List<ConstructorArgDefinition> getConstructorArgList() {
        return constructorArgList;
    }

    @Override
    public void setConstructorArgList(List<ConstructorArgDefinition> constructorArgList) {
        this.constructorArgList = constructorArgList;
    }

    @Override
    public List<PropertyArgDefinition> getPropertyArgList() {
        return propertyArgList;
    }

    @Override
    public void setPropertyArgList(List<PropertyArgDefinition> propertyArgList) {
        this.propertyArgList = propertyArgList;
    }

    @Override
    public boolean isAbstractClass() {
        return abstractClass;
    }

    @Override
    public void setAbstractClass(boolean abstractClass) {
        this.abstractClass = abstractClass;
    }

    @Override
    public String getParentName() {
        return parentName;
    }

    @Override
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public BeanSourceTypeEnum getBeanSourceType() {
        return beanSourceType;
    }

    @Override
    public void setBeanSourceType(BeanSourceTypeEnum beanSourceType) {
        this.beanSourceType = beanSourceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultBeanDefinition that = (DefaultBeanDefinition) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(className, that.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, className);
    }
}

