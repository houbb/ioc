package com.github.houbb.ioc.support.lifecycle.destroy;

import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.Optional;
import com.github.houbb.ioc.exception.IocRuntimeException;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.support.lifecycle.DisposableBean;
import com.github.houbb.ioc.support.lifecycle.InitializingBean;
import com.github.houbb.ioc.util.ClassUtils;

import javax.annotation.PreDestroy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 默认创建对象初始化
 * <p> project: ioc-DefaultCreateBeanInit </p>
 * <p> create on 2019/11/8 21:37 </p>
 *
 * 1. Methods annotated with {@link javax.annotation.PreDestroy}
 * 2. destroy() as defined by the {@link com.github.houbb.ioc.support.lifecycle.DisposableBean} callback interface
 * 3. A custom configured destroy() method
 *
 * 针对注解（1）的处理，需要 class 信息结合 {@link BeanDefinition} 进行注解相关信息的拓展。
 *
 *
 * [spring注解之@PreDestroy的实现原理](https://www.jianshu.com/p/70d18e65a1d5)
 *
 * @author Administrator
 * @since 0.0.4
 */
public class DefaultPreDestroyBean implements DisposableBean {

    /**
     * 对象实例
     * @since 0.0.4
     */
    private final Object instance;

    /**
     * 对象属性定义
     * @since 0.0.4
     */
    private final BeanDefinition beanDefinition;

    public DefaultPreDestroyBean(Object instance, BeanDefinition beanDefinition) {
        this.instance = instance;
        this.beanDefinition = beanDefinition;
    }

    @Override
    public void destroy() {
        preDestroy();

        disposableBean();

        customDestroy();
    }

    /**
     * 注解处理
     * @since 0.0.4
     */
    private void preDestroy() {
        Optional<Method> methodOptional = ClassUtils.getMethodOptional(instance.getClass(), PreDestroy.class);
        if(methodOptional.isNotPresent()) {
            return;
        }

        //1. 信息校验
        Method method = methodOptional.get();
        Class<?>[] paramTypes = method.getParameterTypes();
        if(ArrayUtil.isNotEmpty(paramTypes)) {
            throw new IocRuntimeException("PostConstruct must be has no params.");
        }

        //2. 反射调用
        try {
            method.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IocRuntimeException(e);
        }
    }

    /**
     * 接口处理
     * @since 0.0.4
     */
    private void disposableBean() {
        if(instance instanceof DisposableBean) {
            DisposableBean disposableBean = (DisposableBean)instance;
            disposableBean.destroy();
        }
    }

    /**
     * 自定义初始化函数
     * @since 0.0.4
     */
    private void customDestroy() {
        String destroyName = beanDefinition.getDestroy();
        if(StringUtil.isEmpty(destroyName)) {
            return;
        }

        try {
            Method method = instance.getClass().getMethod(destroyName);
            if(ObjectUtil.isNull(method)) {
                return;
            }

            method.invoke(instance);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IocRuntimeException(e);
        }
    }

}
