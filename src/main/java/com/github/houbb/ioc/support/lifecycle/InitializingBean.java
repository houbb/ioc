package com.github.houbb.ioc.support.lifecycle;

/**
 * Multiple lifecycle mechanisms configured for the same bean, with different initialization methods, are called as follows:
 *
 * 1. Methods annotated with @PostConstruct
 *
 * 2. initialize() as defined by the InitializingBean callback interface
 *
 * 3. A custom configured init() method
 *
 * <p> project: ioc-InitializingBean </p>
 * <p> create on 2019/11/8 20:53 </p>
 *
 * @author Administrator
 * @since 0.0.4
 */
public interface InitializingBean {

    /**
     * 对象初始化完成之后调用
     * @since 0.0.4
     */
    void initialize();

}
