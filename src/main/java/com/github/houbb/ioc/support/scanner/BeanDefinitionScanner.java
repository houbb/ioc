package com.github.houbb.ioc.support.scanner;

import com.github.houbb.ioc.model.BeanDefinition;

import java.util.Set;

/**
 * <p> project: ioc-BeanDefinitionScanner </p>
 * <p> create on 2019/11/18 20:38 </p>
 *
 * @author Administrator
 * @since 0.1.1
 */
public interface BeanDefinitionScanner {

    /**
     * 扫描指定的包，返回对应的 {@link BeanDefinition} 信息集合
     * @param packageNames 包名称数组
     * @return 结果列表
     * @since 0.1.1
     */
    Set<BeanDefinition> scan(final String ... packageNames);

}
