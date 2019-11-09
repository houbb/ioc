package com.github.houbb.ioc.support.lifecycle.create;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.support.lifecycle.NewInstanceBean;
/**
 * 默认新建对象实例的实现
 * @author binbin.hou
 * @since 0.0.6
 */
@ThreadSafe
public class DefaultNewInstanceBean implements NewInstanceBean {

    /**
     * 单例
     * @since 0.0.6
     */
    private static final DefaultNewInstanceBean INSTANCE = new DefaultNewInstanceBean();

    /**
     * 获取实例
     * @return 实例
     * @since 0.0.6
     */
    public static DefaultNewInstanceBean getInstance() {
        return INSTANCE;
    }

    @Override
    public Object newInstance(BeanFactory beanFactory, BeanDefinition beanDefinition) {
        //1. 工厂方法创建
        Object factoryMethodBean = FactoryMethodNewInstanceBean.getInstance()
                .newInstance(beanFactory, beanDefinition);
        if(ObjectUtil.isNotNull(factoryMethodBean)) {
            return factoryMethodBean;
        }

        //2. 根据构造器创建
        return ConstructorNewInstanceBean.getInstance()
                .newInstance(beanFactory, beanDefinition);
    }

}
