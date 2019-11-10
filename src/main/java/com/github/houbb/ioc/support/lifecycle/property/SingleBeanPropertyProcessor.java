package com.github.houbb.ioc.support.lifecycle.property;

import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.model.PropertyArgDefinition;

import java.util.List;

/**
 * 单个对象属性设置类。
 *
 * 主要分为两个部分：
 *
 * （1）直接根据属性值设置
 * （2）根据引用属性设置
 *
 * <p> project: ioc-BeanPropertyProcessor </p>
 * <p> create on 2019/11/8 20:53 </p>
 *
 * @author houbinbin
 * @since 0.0.7
 */
public interface SingleBeanPropertyProcessor {

    /**
     * 对象初始化完成之后调用
     * @param beanFactory 属性工厂
     * @param instance 对象实例
     * @param propertyArgDefinition 参数信息
     * @since 0.0.7
     */
    void propertyProcessor(final BeanFactory beanFactory,
                           final Object instance,
                           PropertyArgDefinition propertyArgDefinition);

}
