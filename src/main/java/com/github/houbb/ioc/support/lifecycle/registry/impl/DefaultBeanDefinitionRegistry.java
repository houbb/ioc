package com.github.houbb.ioc.support.lifecycle.registry.impl;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.support.lifecycle.registry.BeanDefinitionRegistry;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对象注册
 *
 * （1）该类的职责是负责对象的创建，不关心对应的 bean 的获取
 * 某种角度而言，这个类对于用户可以不那么可见。
 *
 * （2）{@link com.github.houbb.ioc.core.BeanFactory} 更加关心对象的获取。
 *
 * @author binbin.hou
 * @since 0.1.8
 */
public class DefaultBeanDefinitionRegistry implements BeanDefinitionRegistry {

    /**
     * 对象信息 map
     *
     * @since 0.0.1
     */
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * 类型集合
     * （1）主要是为了 type 类型，获取对应的信息为做准备
     * （2）考虑到懒加载的处理。
     *
     * @since 0.0.2
     */
    private Map<Class, Set<String>> typeBeanNameMap = new ConcurrentHashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        ArgUtil.notEmpty(beanName, "beanName");
        ArgUtil.notNull(beanDefinition, "beanDefinition");

        this.beanDefinitionMap.put(beanName, beanDefinition);
        this.registerTypeBeanNames(beanName, beanDefinition);
    }

    @Override
    public void removeBeanDefinition(String beanName) {
        this.beanDefinitionMap.remove(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitionMap.get(beanName);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public List<String> getBeanDefinitionNames() {
        Set<String> nameSet = beanDefinitionMap.keySet();
        return Guavas.newArrayList(nameSet);
    }

    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public Set<String> getBeanNames(Class requiredType) {
        ArgUtil.notNull(requiredType, "requiredType");
        return typeBeanNameMap.get(requiredType);
    }

    /**
     * 注册类型和 beanNames 信息
     *
     * @param beanName       单个 bean 名称
     * @param beanDefinition 对象定义
     * @since 0.0.2
     */
    private void registerTypeBeanNames(final String beanName, final BeanDefinition beanDefinition) {
        final Set<Class> typeSet = getTypeSet(beanDefinition);
        for (Class type : typeSet) {
            Set<String> beanNameSet = typeBeanNameMap.get(type);
            if (ObjectUtil.isNull(beanNameSet)) {
                beanNameSet = Guavas.newHashSet();
            }
            beanNameSet.add(beanName);
            typeBeanNameMap.put(type, beanNameSet);
        }
    }

    /**
     * 获取类型集合
     * （1）当前类信息
     * （2）递归所有的父类和接口类信息
     *
     * @param beanDefinition 对象定义
     * @return 类型集合
     * @since 0.0.8
     */
    private Set<Class> getTypeSet(final BeanDefinition beanDefinition) {
        Set<Class> classSet = Guavas.newHashSet();

        String className = beanDefinition.getClassName();
        Class currentClass = ClassUtil.getClass(className);
        classSet.add(currentClass);

        classSet.addAll(ClassUtil.getAllInterfacesAndSuperClass(currentClass));
        return classSet;
    }

}
