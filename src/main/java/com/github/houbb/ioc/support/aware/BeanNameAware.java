package com.github.houbb.ioc.support.aware;

import com.github.houbb.ioc.exception.IocRuntimeException;

/**
 * <p> project: ioc-BeanNameAware </p>
 * <p> create on 2019/11/12 23:11 </p>
 *
 * @author Administrator
 * @since 0.0.8
 */
public interface BeanNameAware extends Aware {

    /**
     * 设置 bean 的名称
     * @param name bean 名称
     * @throws IocRuntimeException 运行时异常
     * @since 0.0.8
     */
    void setBeanName(String name);

}
