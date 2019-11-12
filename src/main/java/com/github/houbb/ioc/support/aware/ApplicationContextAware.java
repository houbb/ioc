package com.github.houbb.ioc.support.aware;

import com.github.houbb.ioc.context.ApplicationContext;
import com.github.houbb.ioc.exception.IocRuntimeException;

/**
 * 这里可以弱化 BeanFactory 的存在。
 * 就不再支持 BeanFactoryAware 了。
 * <p> project: ioc-BeanNameAware </p>
 * <p> create on 2019/11/12 23:11 </p>
 *
 * @author Administrator
 * @since 0.0.8
 */
public interface ApplicationContextAware extends Aware {

    /**
     * 设置 applicationContext 的信息
     * @param applicationContext 上下文信息
     * @throws IocRuntimeException 运行时异常
     * @since 0.0.8
     */
    void setApplicationContext(ApplicationContext applicationContext) throws IocRuntimeException;

}
