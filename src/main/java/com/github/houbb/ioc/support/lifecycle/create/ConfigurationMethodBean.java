package com.github.houbb.ioc.support.lifecycle.create;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectMethodUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.Optional;
import com.github.houbb.heaven.util.util.SetUtil;
import com.github.houbb.ioc.constant.enums.BeanSourceTypeEnum;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.core.ListableBeanFactory;
import com.github.houbb.ioc.exception.IocRuntimeException;
import com.github.houbb.ioc.model.AnnotationBeanDefinition;
import com.github.houbb.ioc.model.BeanDefinition;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

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
        Method method = getMethod(configInstance, annotationBeanDefinition);
        Object[] paramValues = getParamValues(beanFactory, annotationBeanDefinition);

        final Object beanInstance = ReflectMethodUtil.invoke(configInstance, method, paramValues);
        return Optional.ofNullable(beanInstance);
    }

    /**
     * 获取对应的方法
     * @param configInstance  配置实例
     * @param annotationBeanDefinition 注解信息
     * @return 方法实例
     * @since 0.1.2
     */
    private Method getMethod(final Object configInstance,
                             final AnnotationBeanDefinition annotationBeanDefinition) {
        ArgUtil.notNull(configInstance, "configInstance");
        ArgUtil.notNull(annotationBeanDefinition, "annotationBeanDefinition");

        final Class clazz = configInstance.getClass();
        final String methodName = annotationBeanDefinition.getConfigurationBeanMethod();
        final Class[] paramTypes = annotationBeanDefinition.getConfigBeanMethodParamTypes();
        return ClassUtil.getMethod(clazz, methodName, paramTypes);
    }

    /**
     * 获取参数值
     * @param beanFactory 对象工厂
     * @param annotationBeanDefinition 注解定义
     * @return 结果数组
     * @since 0.1.5
     */
    private Object[] getParamValues(final BeanFactory beanFactory,
                                    final AnnotationBeanDefinition annotationBeanDefinition) {
        List<String> refNames = annotationBeanDefinition.getConfigBeanMethodParamRefs();
        if(CollectionUtil.isEmpty(refNames)) {
            return null;
        }

        // 填充属性名称列表
        this.fillRefName(beanFactory, annotationBeanDefinition);

        Object[] arrays = new Object[refNames.size()];
        for(int i = 0; i < refNames.size(); i++) {
            String refName = refNames.get(i);
            arrays[i] = beanFactory.getBean(refName);
        }
        return arrays;
    }

    /**
     * 填充引用名称
     * @param beanFactory 对象工厂
     * @param annotationBeanDefinition 注解属性名称
     * @since 0.1.5
     */
    private void fillRefName(final BeanFactory beanFactory,
                             final AnnotationBeanDefinition annotationBeanDefinition) {
        if(BeanSourceTypeEnum.isConfigurationBean(annotationBeanDefinition.getBeanSourceType())) {
            final String beanName = annotationBeanDefinition.getName();
            Class[] paramTypes = annotationBeanDefinition.getConfigBeanMethodParamTypes();
            final ListableBeanFactory listableBeanFactory = (ListableBeanFactory)beanFactory;
            if(ArrayUtil.isNotEmpty(paramTypes)) {
                List<String> paramRefs = annotationBeanDefinition.getConfigBeanMethodParamRefs();
                for(int i = 0; i < paramTypes.length; i++) {
                    Set<String> beanNames = listableBeanFactory.getBeanNames(paramTypes[i]);
                    if(CollectionUtil.isEmpty(beanNames)) {
                        throw new IocRuntimeException(beanName + " configuration method param of ["+i+"] not found!");
                    }

                    // 如果该类型对应的属性唯一，则直接设置
                    if(beanNames.size() == 1) {
                        paramRefs.set(i, SetUtil.getFirst(beanNames));
                    } else {
                        // 此时不关注是否正确。
                        String paramName = paramRefs.get(i);
                        if(StringUtil.isEmpty(paramName)) {
                            // 此时处理 @Primary 对应的情况

                            throw new IocRuntimeException(beanName + " configuration method param of ["+i+"] must be unique!");
                        }
                    }
                }
            }
        }
    }

}
