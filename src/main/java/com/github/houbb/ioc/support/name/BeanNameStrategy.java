package com.github.houbb.ioc.support.name;

import com.github.houbb.ioc.model.BeanDefinition;

/**
 * Bean 命名策略
 * <p> project: ioc-BeanNameStrategy </p>
 * <p> create on 2019/11/19 22:39 </p>
 *
 * @author Administrator
 * @since 0.1.1
 */
public interface BeanNameStrategy {

    /**
     * 生成对象名称
     * @param definition bean 属性定义
     * @return 生成的结果名称
     * @since 0.1.1
     */
    String generateBeanName(BeanDefinition definition);

}
