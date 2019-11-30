package com.github.houbb.ioc.support.lifecycle.property.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectFieldUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectMethodUtil;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.model.PropertyArgDefinition;
import com.github.houbb.ioc.support.converter.StringValueConverterFactory;
import com.github.houbb.ioc.support.lifecycle.property.SingleBeanPropertyProcessor;

import java.lang.reflect.Field;

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
class ValueBeanPropertyProcessor implements SingleBeanPropertyProcessor {

    private static final ValueBeanPropertyProcessor INSTANCE = new ValueBeanPropertyProcessor();

    public static ValueBeanPropertyProcessor getInstance() {
        return INSTANCE;
    }

    @Override
    public void propertyProcessor(BeanFactory beanFactory, Object instance, PropertyArgDefinition propertyArgDefinition) {
        String valueStr = propertyArgDefinition.getValue();
        String typeStr = propertyArgDefinition.getType();
        boolean fieldBase = propertyArgDefinition.isFieldBase();
        String fieldName = propertyArgDefinition.getName();

        Class type = ClassUtil.getClass(typeStr);
        Object value = StringValueConverterFactory.convertValue(valueStr, type);

        if(fieldBase) {
            ReflectFieldUtil.setValue(instance, fieldName, value);
        } else {
            ReflectMethodUtil.invokeSetterMethod(instance, fieldName, value);
        }
    }

}
