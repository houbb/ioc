package com.github.houbb.ioc.core;

import java.util.List;
import java.util.Set;

/**
 * bean 工厂接口
 * @author binbin.hou
 * @since 0.0.1
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 获取指定类型的实现
     * （1）如果对应类型不存在，则直接抛出异常
     * （2）返回对应的列表信息
     * @param requiredType 类型
     * @param <T> 泛型
     * @return bean 列表
     * @since 0.0.2
     */
    <T> List<T> getBeans(final Class<T> requiredType);

    /**
     * 根据类型获取对应的 bean 名称列表
     * @param requiredType 类型
     * @return 对应的列表
     * @since 0.1.5
     */
    Set<String> getBeanNames(final Class requiredType);

    /**
     * 获取指定类型的对象
     * @param requiredType 指定类型
     * @param beanName 属性名称，可能为空
     * @param <T> 泛型
     * @return 结果
     * @since 0.1.6
     * @throws com.github.houbb.ioc.exception.IocRuntimeException 如果对应的信息不唯一
     * @see com.github.houbb.ioc.annotation.Primary 优先选择，如果当 beanName 为空时
     * @since 0.1.6
     */
    <T> T getRequiredTypeBean(final Class<T> requiredType, final String beanName);

}
