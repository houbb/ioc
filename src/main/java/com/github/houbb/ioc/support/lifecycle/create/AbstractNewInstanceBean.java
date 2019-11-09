package com.github.houbb.ioc.support.lifecycle.create;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.util.Optional;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.support.lifecycle.NewInstanceBean;

/**
 * 抽象对象实例的实现
 * @author binbin.hou
 * @since 0.0.6
 */
@ThreadSafe
abstract class AbstractNewInstanceBean implements NewInstanceBean {

    /**
     * 创建新对象实例
     * @param beanFactory 对象工厂
     * @param beanClass 类信息
     * @param beanDefinition 对象定义信息
     * @return 实例 optional 信息
     * @since 0.0.6
     */
    protected abstract Optional<Object> newInstanceOpt(final BeanFactory beanFactory,
                                                       final BeanDefinition beanDefinition,
                                                       final Class beanClass);

    @Override
    public Object newInstance(final BeanFactory beanFactory, BeanDefinition beanDefinition) {
        ArgUtil.notNull(beanFactory, "beanFactory");
        ArgUtil.notNull(beanDefinition, "beanDefinition");

        final Class beanClass = ClassUtil.getClass(beanDefinition.getClassName());
        Optional<Object> optional = newInstanceOpt(beanFactory, beanDefinition, beanClass);
        return optional.orElseNull();
    }

}
