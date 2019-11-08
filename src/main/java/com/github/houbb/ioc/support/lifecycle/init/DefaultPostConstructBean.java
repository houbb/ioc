package com.github.houbb.ioc.support.lifecycle.init;

import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.Optional;
import com.github.houbb.ioc.exception.IocRuntimeException;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.support.lifecycle.InitializingBean;
import com.github.houbb.ioc.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 默认创建对象初始化
 * <p> project: ioc-DefaultCreateBeanInit </p>
 * <p> create on 2019/11/8 21:37 </p>
 *
 * （1）注解 {@link javax.annotation.PostConstruct}
 * （2）添加 {@link com.github.houbb.ioc.support.lifecycle.InitializingBean} 初始化相关处理
 * （3）添加 {@link BeanDefinition#getInitialize()} 初始化相关处理
 *
 * 针对注解（1）的处理，需要 class 信息结合 {@link BeanDefinition} 进行注解相关信息的拓展。
 *
 * @author Administrator
 * @since 0.0.4
 */
public class DefaultPostConstructBean implements InitializingBean {

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

    public DefaultPostConstructBean(Object instance, BeanDefinition beanDefinition) {
        this.instance = instance;
        this.beanDefinition = beanDefinition;
    }

    @Override
    public void initialize() {

        postConstruct();

        initializingBean();

        customInit();
    }

    /**
     * 注解处理
     * @since 0.0.4
     */
    private void postConstruct() {
        Optional<Method> methodOptional = ClassUtils.getMethodOptional(instance.getClass(), PostConstruct.class);
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
    private void initializingBean() {
        if(instance instanceof InitializingBean) {
            InitializingBean initializingBean = (InitializingBean)instance;
            initializingBean.initialize();
        }
    }

    /**
     * 自定义初始化函数
     * @since 0.0.4
     */
    private void customInit() {
        String initName = beanDefinition.getInitialize();
        if(StringUtil.isEmpty(initName)) {
            return;
        }

        try {
            Method method = instance.getClass().getMethod(initName);
            if(ObjectUtil.isNull(method)) {
                return;
            }

            method.invoke(instance);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IocRuntimeException(e);
        }
    }

}
