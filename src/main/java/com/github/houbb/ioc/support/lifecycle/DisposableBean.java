package com.github.houbb.ioc.support.lifecycle;

/**
 * 对象被销毁之前调用
 *
 * Destroy methods are called in the same order:
 *
 * 1. Methods annotated with @PreDestroy
 *
 * 2. destroy() as defined by the DisposableBean callback interface
 *
 * 3. A custom configured destroy() method
 *
 * <p> project: ioc-DisposableBean </p>
 * <p> create on 2019/11/8 20:55 </p>
 *
 * @author Administrator
 * @since 0.0.4
 */
public interface DisposableBean {

    /**
     * 销毁方法
     * @since 0.0.4
     */
    void destroy();

}
