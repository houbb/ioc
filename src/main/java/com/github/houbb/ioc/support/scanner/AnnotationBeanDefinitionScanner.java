package com.github.houbb.ioc.support.scanner;

import com.github.houbb.ioc.model.AnnotationBeanDefinition;
import com.github.houbb.ioc.model.BeanDefinition;

import java.util.Set;

/**
 * <p> project: ioc-BeanDefinitionScanner </p>
 * <p> create on 2019/11/18 20:38 </p>
 *
 * @author Administrator
 * @since 0.1.11
 * @see com.github.houbb.ioc.annotation.ComponentScan 包扫描
 * @see com.github.houbb.ioc.annotation.Component 组件注解
 */
public interface AnnotationBeanDefinitionScanner {

    /**
     * 扫描指定的包，返回对应的 {@link AnnotationBeanDefinition} 信息集合
     * @param context 上下文
     * @return 结果列表
     * @since 0.1.11
     */
    Set<AnnotationBeanDefinition> scan(final BeanDefinitionScannerContext context);

}
