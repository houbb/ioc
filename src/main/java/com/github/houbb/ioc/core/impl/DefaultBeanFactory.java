package com.github.houbb.ioc.core.impl;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.exception.IocRuntimeException;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.util.ClassUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * bean 工厂接口
 * @author binbin.hou
 * @since 0.0.1
 */
public class DefaultBeanFactory implements BeanFactory {

    /**
     * 对象信息 map
     * @since 0.0.1
     */
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * 对象 map
     * @since 0.0.1
     */
    private Map<String, Object> beanMap = new ConcurrentHashMap<>();

    /**
     * 类型集合
     * （1）主要是为了 type 类型，获取对应的信息为做准备
     * （2）考虑到懒加载的处理。
     * @see #getBean(String, Class) 获取对应对象信息
     * @since 0.0.2
     */
    private Map<Class, Set<String>> typeBeanNameMap = new ConcurrentHashMap<>();

    /**
     * 注册对象定义信息
     * @since 0.0.1
     */
    protected void registerBeanDefinition(final String beanName, final BeanDefinition beanDefinition) {
        // 这里可以添加监听器
        this.beanDefinitionMap.put(beanName, beanDefinition);

        //@since 0.0.2 类型信息
        final Class type = getType(beanDefinition);
        Set<String> beanNameSet = typeBeanNameMap.get(type);
        if(ObjectUtil.isNull(beanNameSet)) {
            beanNameSet = Guavas.newHashSet();
        }
        beanNameSet.add(beanName);
        typeBeanNameMap.put(type, beanNameSet);
    }

    /**
     * 根据类型获取对应的属性名称
     * @param requiredType 需求类型
     * @return bean 名称列表
     * @since 0.0.2
     */
    Set<String> getBeanNames(final Class requiredType) {
        ArgUtil.notNull(requiredType, "requiredType");
        return typeBeanNameMap.get(requiredType);
    }

    @Override
    public Object getBean(String beanName) {
        ArgUtil.notNull(beanName, "beanName");

        Object bean = beanMap.get(beanName);
        if(ObjectUtil.isNotNull(bean)) {
            // 这里直接返回的是单例，如果用户指定为多例，则每次都需要新建。
            return bean;
        }

        // 获取对应配置信息
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(ObjectUtil.isNull(beanDefinition)) {
            throw new IocRuntimeException(beanName + " not exists in bean define.");
        }

        // 直接根据
        Object newBean = createBean(beanDefinition);
        // 这里可以添加对应的监听器
        beanMap.put(beanName, newBean);
        return newBean;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanName, Class<T> requiredType) {
        ArgUtil.notNull(beanName, "beanName");
        ArgUtil.notNull(requiredType, "requiredType");

        Object object = getBean(beanName);
        return (T)object;
    }

    @Override
    public boolean containsBean(String beanName) {
        ArgUtil.notNull(beanName, "beanName");

        return beanDefinitionMap.keySet().contains(beanName);
    }

    @Override
    public boolean isTypeMatch(String beanName, Class requiredType) {
        ArgUtil.notNull(beanName, "beanName");
        ArgUtil.notNull(requiredType, "requiredType");

        Class<?> beanType = getType(beanName);
        return requiredType.equals(beanType);
    }

    @Override
    public Class<?> getType(String beanName) {
        ArgUtil.notNull(beanName, "beanName");

        Object bean = this.getBean(beanName);
        return bean.getClass();
    }

    /**
     * 根据对象定义信息创建对象
     * @param beanDefinition 对象定义信息
     * @return 创建的对象信息
     * @since 0.0.1
     */
    private Object createBean(final BeanDefinition beanDefinition) {
        String className = beanDefinition.getClassName();
        Class clazz = ClassUtils.getClass(className);
        return ClassUtils.newInstance(clazz);
    }

    /**
     * 获取类型信息
     * （1）这里主要是一种
     * @param beanDefinition 对象属性
     * @return 对应的 bean 信息
     * @since 0.0.2
     */
    private Class getType(final BeanDefinition beanDefinition) {
        String className = beanDefinition.getClassName();
        return ClassUtils.getClass(className);
    }

}
