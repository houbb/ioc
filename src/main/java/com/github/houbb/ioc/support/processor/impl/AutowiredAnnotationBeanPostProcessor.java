package com.github.houbb.ioc.support.processor.impl;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassTypeUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectFieldUtil;
import com.github.houbb.heaven.util.lang.reflect.TypeUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.ioc.annotation.Autowired;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.core.ListableBeanFactory;
import com.github.houbb.ioc.exception.IocRuntimeException;
import com.github.houbb.ioc.support.aware.BeanFactoryAware;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * 注解扫描中对于 {@link com.github.houbb.ioc.annotation.Autowired} 注解的处理
 * @author binbin.hou
 * @since 0.1.6
 */
public class AutowiredAnnotationBeanPostProcessor extends BeanPostProcessorAdaptor
        implements BeanFactoryAware {

    /**
     * 对象工厂
     * @since 0.1.6
     */
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object afterPropertySet(String beanName, Object instance) {
        ArgUtil.notNull(instance, "instance");

        final Class clazz = instance.getClass();
        List<Field> fieldList = ClassUtil.getAllFieldList(clazz);

        ListableBeanFactory listableBeanFactory = (ListableBeanFactory)beanFactory;
        for(Field field : fieldList) {
            if(field.isAnnotationPresent(Autowired.class)) {
                Autowired autowired = field.getAnnotation(Autowired.class);

                // 这里可以添加计算，如果是集合，那么返回集合
                final Class fieldTypeClass = field.getType();
                final String value = autowired.value();
                Object fieldRefValue;

                // 集合特殊处理
                if(ClassTypeUtil.isCollection(fieldTypeClass)) {
                    final Type fieldGenericType = field.getGenericType();
                    Class itemClass = TypeUtil.getGenericType(fieldGenericType);
                    List list = listableBeanFactory.getBeans(itemClass);
                    if(CollectionUtil.isEmpty(list)) {
                        throw new IocRuntimeException("Autowired of class type " + itemClass
                            +" not found!");
                    }
                    Collection collection = TypeUtil.createCollection(fieldGenericType);
                    collection.addAll(list);
                    fieldRefValue = collection;
                } else {
                    fieldRefValue = listableBeanFactory.getRequiredTypeBean(fieldTypeClass, value);
                }

                ReflectFieldUtil.setValue(field, instance, fieldRefValue);
            }
        }

        return instance;
    }

}
