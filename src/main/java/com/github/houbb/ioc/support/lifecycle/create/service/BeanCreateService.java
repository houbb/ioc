package com.github.houbb.ioc.support.lifecycle.create.service;

import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.support.cycle.DependsCheckService;
import com.github.houbb.ioc.support.lifecycle.DisposableBean;

/**
 * 需要创建的单例对象
 * @author binbin.hou
 * @since 0.1.8
 */
public interface BeanCreateService extends DisposableBean {

    /**
     * 设置 bean 工厂属性
     * @param beanFactory bean 工厂
     * @since 0.1.8
     */
    void setBeanFactory(final BeanFactory beanFactory);

    /**
     * 设置依赖检查服务类
     * @param dependsCheckService 实现类
     * @since 0.1.8
     */
    void setDependsCheckService(final DependsCheckService dependsCheckService);

    /**
     * 创建需要创建的单例对象
     *
     * @param beanDefinition 对象定义
     * @since 0.1.5
     */
    void createEagerSingleton(final BeanDefinition beanDefinition);

    /**
     * 注册单例且渴望初期初始化的对象
     * （1）如果是 singleton & lazy-init=false 则进行初始化处理
     * （2）创建完成后，对象放入 beanMap 中，便于后期使用
     *
     * @param beanName       bean 名称
     * @param beanDefinition 对象定义
     * @since 0.0.3
     * @return 实例结果
     */
    Object registerSingletonBean(final String beanName, final BeanDefinition beanDefinition);

    /**
     * 根据对象定义信息创建对象
     * （1）注解 {@link javax.annotation.PostConstruct}
     * （2）添加 {@link com.github.houbb.ioc.support.lifecycle.InitializingBean} 初始化相关处理
     * （3）添加 {@link BeanDefinition#getInitialize()} 初始化相关处理
     * <p>
     * 如果想使用注解相关信息，考虑实现 AnnotationBeanDefinition 统一处理注解信息。
     * 本期暂时忽略 (1)
     *
     * @param beanDefinition 对象定义信息
     * @return 创建的对象信息
     * @since 0.0.1
     */
    Object createBean(final BeanDefinition beanDefinition);

}
