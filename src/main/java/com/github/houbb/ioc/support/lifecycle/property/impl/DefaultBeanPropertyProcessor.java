package com.github.houbb.ioc.support.lifecycle.property.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.model.PropertyArgDefinition;
import com.github.houbb.ioc.support.lifecycle.property.BeanPropertyProcessor;

import java.util.List;

/**
 * 对象属性设置类。
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
@ThreadSafe
public class DefaultBeanPropertyProcessor implements BeanPropertyProcessor {

    private static final DefaultBeanPropertyProcessor INSTANCE = new DefaultBeanPropertyProcessor();

    public static DefaultBeanPropertyProcessor getInstance() {
        return INSTANCE;
    }

    @Override
    public void propertyProcessor(BeanFactory beanFactory, Object instance, List<PropertyArgDefinition> propertyArgList) {
        if(CollectionUtil.isEmpty(propertyArgList)
            || ObjectUtil.isNull(instance)) {
            return;
        }

        for(PropertyArgDefinition argDefinition : propertyArgList) {
            if(ObjectUtil.isNull(argDefinition)) {
                continue;
            }

            final String ref = argDefinition.getRef();
            if(StringUtil.isEmpty(ref)) {
                ValueBeanPropertyProcessor.getInstance()
                        .propertyProcessor(beanFactory, instance, argDefinition);
            } else {
                RefBeanPropertyProcessor.getInstance()
                        .propertyProcessor(beanFactory, instance, argDefinition);
            }
        }
    }

}
