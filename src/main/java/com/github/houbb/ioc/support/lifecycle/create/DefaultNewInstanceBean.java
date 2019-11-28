package com.github.houbb.ioc.support.lifecycle.create;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.ioc.constant.enums.BeanSourceTypeEnum;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.core.ListableBeanFactory;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.support.lifecycle.NewInstanceBean;
import com.github.houbb.ioc.support.lifecycle.property.impl.DefaultBeanPropertyProcessor;
import com.github.houbb.ioc.support.processor.BeanPostProcessor;
import com.github.houbb.ioc.support.processor.impl.AutowiredAnnotationBeanPostProcessor;

import java.util.List;

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
        Object instance;
        final BeanSourceTypeEnum sourceType = beanDefinition.getBeanSourceType();

        //1. 工厂方法创建
        Object factoryMethodBean = FactoryMethodNewInstanceBean.getInstance()
                .newInstance(beanFactory, beanDefinition);
        if(ObjectUtil.isNotNull(factoryMethodBean)) {
            instance = factoryMethodBean;
        } else if(BeanSourceTypeEnum.CONFIGURATION_BEAN.equals(sourceType)) {
            //config-bean
            instance = ConfigurationMethodBean.getInstance().newInstance(beanFactory, beanDefinition);
        } else {
            //3 根据构造器创建
            instance = ConstructorNewInstanceBean.getInstance()
                    .newInstance(beanFactory, beanDefinition);
        }

        //2.1 通知 BeanPostProcessor
        final String beanName = beanDefinition.getName();
        List<BeanPostProcessor> beanPostProcessorList = getBeanPostProcessorList(beanFactory);
        for(BeanPostProcessor processor : beanPostProcessorList) {
            instance = processor.beforePropertySet(beanName, instance);
        }

        //3. 属性设置
        DefaultBeanPropertyProcessor.getInstance()
                .propertyProcessor(beanFactory, instance, beanDefinition.getPropertyArgList());
        for(BeanPostProcessor processor : beanPostProcessorList) {
            instance = processor.afterPropertySet(beanName, instance);
        }

        //4. 返回结果
        return instance;
    }

    /**
     * 获取 {@link BeanPostProcessor} 对应的列表信息
     * @param beanFactory 对象工厂
     * @return 对象列表
     * @since 0.1.6
     */
    private List<BeanPostProcessor> getBeanPostProcessorList(final BeanFactory beanFactory) {
        ListableBeanFactory listableBeanFactory = (ListableBeanFactory)beanFactory;
        List<BeanPostProcessor> beanPostProcessorList = listableBeanFactory.getBeans(BeanPostProcessor.class);

        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setBeanFactory(listableBeanFactory);
        beanPostProcessorList.add(beanPostProcessor);
        return beanPostProcessorList;
    }

}
