package com.github.houbb.ioc.support.scanner;

import com.github.houbb.ioc.support.name.BeanNameStrategy;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 扫描上下文
 * <p> project: ioc-BeanDefinitionScanner </p>
 * <p> create on 2019/11/18 20:38 </p>
 *
 * @author Administrator
 * @since 0.1.11
 * @see com.github.houbb.ioc.annotation.ComponentScan 包扫描
 * @see com.github.houbb.ioc.annotation.Component 组件注解
 */
public interface BeanDefinitionScannerContext {

    /**
     * 获取需要扫描的包列表
     * @return 包
     * @since 0.1.11
     */
    List<String> getScanPackages();

    /**
     * 获取所有需要排除的类列表
     * @return 类列表
     * @since 0.1.11
     */
    List<Class> getExcludes();

    /**
     * 获取所有需要包含的类列表
     * @return 类列表
     * @since 0.1.11
     */
    List<Class> getIncludes();

    /**
     * 获取对象名称策略
     * @return 名称策略类
     * @since 0.1.11
     */
    Class<? extends BeanNameStrategy> getBeanNameStrategy();

}
