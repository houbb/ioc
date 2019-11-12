package com.github.houbb.ioc.support.processor;

/**
 * 用来标识可以修改的接口类
 * <p> project: ioc-Processor </p>
 * <p> create on 2019/11/12 23:58 </p>
 *
 * @author Administrator
 * @since 0.0.8
 */
public interface BeanPostProcessor extends PostProcessor {

    /**
     * 属性设置之前
     * @param beanName 对象名称
     * @param instance 实例
     * @return 结果
     * @since 0.0.8
     */
    Object beforePropertySet(final String beanName, final Object instance);

    /**
     * 属性设置之后
     * @param beanName 对象名称
     * @param instance 实例
     * @return 结果
     * @since 0.0.8
     */
    Object afterPropertySet(final String beanName, final Object instance);

}
