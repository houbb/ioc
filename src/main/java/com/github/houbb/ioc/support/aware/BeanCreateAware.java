package com.github.houbb.ioc.support.aware;

import com.github.houbb.ioc.exception.IocRuntimeException;

/**
 * 对象创建监听
 * <p> project: ioc-BeanNameAware </p>
 * <p> create on 2019/11/12 23:11 </p>
 *
 * @author Administrator
 * @since 0.0.8
 */
public interface BeanCreateAware extends Aware {

    /**
     * 设值创建的对象
     * @param name bean 名称
     * @param instance 实例
     * @throws IocRuntimeException 运行时异常
     * @since 0.0.8
     */
    void setBeanCreate(String name, final Object instance);

}
