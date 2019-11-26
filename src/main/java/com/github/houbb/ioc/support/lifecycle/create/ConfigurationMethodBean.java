package com.github.houbb.ioc.support.lifecycle.create;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectMethodUtil;
import com.github.houbb.heaven.util.util.Optional;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.exception.IocRuntimeException;
import com.github.houbb.ioc.model.AnnotationBeanDefinition;
import com.github.houbb.ioc.model.BeanDefinition;

import java.lang.reflect.Method;

/**
 * 根据构造器创建对象
 * @author binbin.hou
 * @since 0.1.2
 */
@ThreadSafe
class ConfigurationMethodBean extends AbstractNewInstanceBean {

    /**
     * 单例
     * @since 0.1.2
     */
    private static final ConfigurationMethodBean INSTANCE = new ConfigurationMethodBean();

    /**
     * 获取单例实例
     * @return 单例
     * @since 0.1.2
     */
    public static ConfigurationMethodBean getInstance() {
        return INSTANCE;
    }

    @Override
    protected Optional<Object> newInstanceOpt(BeanFactory beanFactory, BeanDefinition beanDefinition, Class beanClass) {
        if(!(beanDefinition instanceof AnnotationBeanDefinition)) {
            throw new IocRuntimeException("beanDefinition must be instance of AnnotationBeanDefinition " + beanDefinition.getName());
        }

        // 直接根据 invoke
        AnnotationBeanDefinition annotationBeanDefinition = (AnnotationBeanDefinition)beanDefinition;
        Object configInstance = beanFactory.getBean(annotationBeanDefinition.getConfigurationName());
        Method method = getMethod(configInstance, annotationBeanDefinition.getConfigurationBeanMethod());

        final Object beanInstance = ReflectMethodUtil.invoke(configInstance, method);
        return Optional.ofNullable(beanInstance);
    }

    /**
     * 获取对应的方法
     * @param configInstance  配置实例
     * @param methodName 方法名称
     * @return 方法实例
     * @since 0.1.2
     */
    private Method getMethod(final Object configInstance,
                             final String methodName) {
        ArgUtil.notNull(configInstance, "configInstance");
        ArgUtil.notEmpty(methodName, "methodName");

        final Class clazz = configInstance.getClass();
        return ClassUtil.getMethod(clazz, methodName);
    }

}
