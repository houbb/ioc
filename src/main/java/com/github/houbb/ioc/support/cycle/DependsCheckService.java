package com.github.houbb.ioc.support.cycle;

import com.github.houbb.ioc.model.BeanDefinition;

import java.util.List;

/**
 * 依赖检查服务
 * <p> project: ioc-CycleService </p>
 * <p> create on 2019/11/13 22:14 </p>
 *
 * @author Administrator
 * @since 0.1.0
 */
public interface DependsCheckService {

    /**
     * 注册所有的 bean 定义信息
     * @param beanDefinitionList bean 定义信息
     * @since 0.1.0
     */
    void registerBeanDefinitions(final List<BeanDefinition> beanDefinitionList);

    /**
     * 是否是循环依赖
     * @param beanName 对象名称
     * @return 是否依赖
     * @since 0.1.0
     */
    boolean isCircleRef(String beanName);

}
