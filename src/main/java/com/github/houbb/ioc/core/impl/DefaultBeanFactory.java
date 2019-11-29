package com.github.houbb.ioc.core.impl;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.ioc.constant.enums.ScopeEnum;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.exception.IocRuntimeException;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.support.aware.service.AwareService;
import com.github.houbb.ioc.support.aware.service.impl.DefaultAwareService;
import com.github.houbb.ioc.support.cycle.DependsCheckService;
import com.github.houbb.ioc.support.cycle.impl.DefaultDependsCheckService;
import com.github.houbb.ioc.support.lifecycle.DisposableBean;
import com.github.houbb.ioc.support.lifecycle.create.service.BeanCreateService;
import com.github.houbb.ioc.support.lifecycle.create.service.impl.DefaultBeanCreateService;
import com.github.houbb.ioc.support.lifecycle.registry.BeanDefinitionRegistry;
import com.github.houbb.ioc.support.lifecycle.registry.impl.DefaultBeanDefinitionRegistry;

/**
 * bean 工厂接口
 *
 * @author binbin.hou
 * @since 0.0.1
 */
public class DefaultBeanFactory implements BeanFactory, DisposableBean {

    /**
     * 依赖检测服务
     *
     * @since 0.1.0
     */
    protected DependsCheckService dependsCheckService = new DefaultDependsCheckService();

    /**
     * 对象信息注册
     *
     * @since 0.1.8
     */
    protected BeanDefinitionRegistry beanDefinitionRegistry = new DefaultBeanDefinitionRegistry();

    /**
     * 对象创建服务类
     * @since 0.1.8
     */
    protected BeanCreateService beanCreateService = new DefaultBeanCreateService();

    /**
     * 监听通知服务类
     * @since 0.1.8
     */
    protected AwareService awareService = new DefaultAwareService();

    public DefaultBeanFactory() {
        awareService.setBeanFactory(this);

        beanCreateService.setBeanFactory(this);
        beanCreateService.setDependsCheckService(this.dependsCheckService);
    }

    /**
     * 注册对象定义信息
     *
     * @param beanName       属性信息
     * @param beanDefinition 对象定义
     * @since 0.0.1
     */
    protected void registerBeanDefinition(final String beanName, final BeanDefinition beanDefinition) {
        beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);

        awareService.notifyAllBeanNameAware(beanName);
    }

    @Override
    public Object getBean(String beanName) {
        ArgUtil.notNull(beanName, "beanName");
        // 获取对应配置信息
        BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(beanName);
        if (ObjectUtil.isNull(beanDefinition)) {
            throw new IocRuntimeException(beanName + " not exists in bean define.");
        }

        // 如果为多例，直接创建新的对象即可。
        final String scope = beanDefinition.getScope();
        if (!ScopeEnum.SINGLETON.getCode().equals(scope)) {
            return beanCreateService.createBean(beanDefinition);
        }

        // 单例的流程
        return beanCreateService.registerSingletonBean(beanName, beanDefinition);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanName, Class<T> requiredType) {
        ArgUtil.notNull(beanName, "beanName");
        ArgUtil.notNull(requiredType, "requiredType");

        Object object = getBean(beanName);
        return (T) object;
    }

    @Override
    public boolean containsBean(String beanName) {
        ArgUtil.notNull(beanName, "beanName");
        return beanDefinitionRegistry.containsBeanDefinition(beanName);
    }

    @Override
    public boolean isTypeMatch(String beanName, Class requiredType) {
        ArgUtil.notNull(beanName, "beanName");
        ArgUtil.notNull(requiredType, "requiredType");

        Class<?> beanType = getType(beanName);
        return requiredType.equals(beanType);
    }

    @Override
    public Class<?> getType(String beanName) {
        ArgUtil.notNull(beanName, "beanName");

        Object bean = this.getBean(beanName);
        return bean.getClass();
    }

    @Override
    public void destroy() {
    }

}
