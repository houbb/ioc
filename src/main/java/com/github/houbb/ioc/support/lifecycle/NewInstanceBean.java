package com.github.houbb.ioc.support.lifecycle;

import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.model.BeanDefinition;

/**
 * 创建一个对象，有四种方式，你会吗？
 *
 * （1）直接使用 {@link BeanDefinition#getFactoryMethod()} 创建对象
 * （2）根据构造器创建对象
 *  2.1 默认无参构造器
 *  2.2 通过指定的构造器信息创建
 *
 * <p> project: ioc-InitializingBean </p>
 * <p> create on 2019/11/8 20:53 </p>
 *
 * @author Administrator
 * @since 0.0.6
 */
public interface NewInstanceBean {

    /**
     * 对象初始化完成之后调用
     * @param beanFactory 属性工厂
     * @param beanDefinition 对象基本信息定义
     * @return 对象实例
     * @since 0.0.6
     */
    Object newInstance(final BeanFactory beanFactory, final BeanDefinition beanDefinition);

}
