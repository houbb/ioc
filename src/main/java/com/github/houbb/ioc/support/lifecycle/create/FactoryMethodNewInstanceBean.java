package com.github.houbb.ioc.support.lifecycle.create;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectMethodUtil;
import com.github.houbb.heaven.util.util.Optional;
import com.github.houbb.ioc.annotation.FactoryMethod;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.model.BeanDefinition;

import java.lang.reflect.Method;

/**
 * 根据静态方法创建对象实例
 * @author binbin.hou
 * @since 0.0.6
 */
@ThreadSafe
class FactoryMethodNewInstanceBean extends AbstractNewInstanceBean {

    /**
     * 创建对象实例
     * @since 0.0.6
     */
    private static final FactoryMethodNewInstanceBean INSTANCE = new FactoryMethodNewInstanceBean();

    /**
     * 获取对象实例
     * @since 0.0.6
     */
    static FactoryMethodNewInstanceBean getInstance() {
        return INSTANCE;
    }


    @Override
    protected Optional<Object> newInstanceOpt(BeanFactory beanFactory, BeanDefinition beanDefinition, Class beanClass) {
        //1. 指定了该方法
        Optional<Object> customOpt = newInstance(beanClass, beanDefinition.getFactoryMethod());
        if(customOpt.isPresent()) {
            return customOpt;
        }

        //2. 通过注解指定
        return newInstance(beanClass);
    }

    /**
     * 根据指定的方法名称创建对象信息
     * @param beanClass 对象信息
     * @param factoryMethodName 方法名称
     * @return 实例结果
     * @since 0.0.6
     */
    private Optional<Object> newInstance(final Class beanClass,
                                               final String factoryMethodName) {
        if(StringUtil.isNotEmpty(factoryMethodName)) {
            final Method factoryMethod = ClassUtil.getMethod(beanClass, factoryMethodName);
            Object result = ReflectMethodUtil.invokeFactoryMethod(beanClass, factoryMethod);
            return Optional.of(result);
        }

        return Optional.empty();
    }

    /**
     * 根据注解获取对应的信息
     * @param beanClass bean class 信息
     * @return 实例
     * @since 0.0.6
     */
    private Optional<Object> newInstance(final Class beanClass) {
        Optional<Method> methodOptional = ReflectMethodUtil.getMethodOptional(beanClass, FactoryMethod.class);

        if(methodOptional.isNotPresent()) {
            return Optional.empty();
        }

        Method factoryMethod = methodOptional.get();
        Object result = ReflectMethodUtil.invokeFactoryMethod(beanClass, factoryMethod);
        return Optional.of(result);
    }

}
