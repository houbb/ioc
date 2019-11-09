package com.github.houbb.ioc.support.lifecycle.init;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectMethodUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.Optional;
import com.github.houbb.ioc.exception.IocRuntimeException;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.support.lifecycle.InitializingBean;

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
     * 对象实例类型
     * @since 0.0.5
     */
    private final Class instanceClass;

    /**
     * 对象属性定义
     * @since 0.0.4
     */
    private final BeanDefinition beanDefinition;

    public DefaultPostConstructBean(Object instance, BeanDefinition beanDefinition) {
        ArgUtil.notNull(instance, "instance");
        ArgUtil.notNull(beanDefinition, "beanDefinition");

        this.instance = instance;
        this.beanDefinition = beanDefinition;
        this.instanceClass = instance.getClass();
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
        Optional<Method> methodOptional = ReflectMethodUtil.getMethodOptional(instanceClass, PostConstruct.class);
        if(methodOptional.isNotPresent()) {
            return;
        }

        // 执行调用
        ReflectMethodUtil.invokeNoArgsMethod(instance, methodOptional.get());
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

        final Method method = ClassUtil.getMethod(instanceClass, initName);
        ReflectMethodUtil.invokeNoArgsMethod(instance, method);
    }

}
