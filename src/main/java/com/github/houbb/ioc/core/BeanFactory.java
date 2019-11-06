package com.github.houbb.ioc.core;

/**
 * bean 工厂接口
 * @author binbin.hou
 * @since 0.0.1
 */
public interface BeanFactory {

    /**
     * 根据名称获取对应的实例信息
     * @param beanName bean 名称
     * @return 对象信息
     * @since 0.0.1
     */
    Object getBean(final String beanName);

    /**
     * 获取指定类型的实现
     * @param beanName 属性名称
     * @param tClass 类型
     * @param <T> 泛型
     * @return 结果
     * @since 0.0.1
     */
    <T> T getBean(final String beanName, final Class<T> tClass);

}
