package com.github.houbb.ioc.core.impl;

import com.github.houbb.heaven.support.tuple.impl.Pair;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.ioc.constant.enums.ScopeEnum;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.exception.IocRuntimeException;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.model.ConstructorArgDefinition;
import com.github.houbb.ioc.support.cycle.DependsCheckService;
import com.github.houbb.ioc.support.cycle.impl.DefaultDependsCheckService;
import com.github.houbb.ioc.support.lifecycle.DisposableBean;
import com.github.houbb.ioc.support.lifecycle.InitializingBean;
import com.github.houbb.ioc.support.lifecycle.create.DefaultNewInstanceBean;
import com.github.houbb.ioc.support.lifecycle.destroy.DefaultPreDestroyBean;
import com.github.houbb.ioc.support.lifecycle.init.DefaultPostConstructBean;
import com.github.houbb.ioc.support.lifecycle.registry.BeanDefinitionRegistry;
import com.github.houbb.ioc.support.lifecycle.registry.impl.DefaultBeanDefinitionRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * bean 工厂接口
 *
 * @author binbin.hou
 * @since 0.0.1
 */
public class DefaultBeanFactory implements BeanFactory, DisposableBean {

    /**
     * 对象 map
     *
     * @since 0.0.1
     */
    private Map<String, Object> beanMap = new ConcurrentHashMap<>();

    /**
     * 实例于 bean 定义信息 map
     *
     * @since 0.0.4
     */
    private List<Pair<Object, BeanDefinition>> instanceBeanDefinitionList = new ArrayList<>();

    /**
     * 依赖检测服务
     *
     * @since 0.1.0
     */
    private DependsCheckService dependsCheckService = new DefaultDependsCheckService();

    /**
     * 对象信息注册
     *
     * @since 0.1.8
     */
    protected BeanDefinitionRegistry beanDefinitionRegistry = new DefaultBeanDefinitionRegistry();

    /**
     * 注册对象定义信息
     *
     * @param beanName       属性信息
     * @param beanDefinition 对象定义
     * @since 0.0.1
     */
    protected void registerBeanDefinition(final String beanName, final BeanDefinition beanDefinition) {
        beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
    }

    /**
     * 创建需要创建的单例对象
     *
     * @param beanDefinition 对象定义
     * @since 0.1.5
     */
    protected void createEagerSingleton(final BeanDefinition beanDefinition) {
        //3. 初始化 bean 信息-这里应该统一放在最后进行初始化。
        final String beanName = beanDefinition.getName();
        if (needEagerCreateSingleton(beanDefinition)) {
            this.registerSingletonBean(beanName, beanDefinition);
        }
    }

    /**
     * 获取依赖检测服务
     *
     * @return 服务实现
     * @since 0.1.0
     */
    protected DependsCheckService getDependsCheckService() {
        return this.dependsCheckService;
    }

    /**
     * 是否需要新建单例
     * （1）如果不需要立刻加载，则进行延迟处理
     * （2）如果存在构造器创建，则判断是否存在 dependsOn
     * 如果 {@link #beanMap} 中包含所有依赖对象，则直接创建，否则需要等待。
     *
     * @param beanDefinition 定义信息
     * @return 是否
     * @since 0.0.7
     */
    private boolean needEagerCreateSingleton(final BeanDefinition beanDefinition) {
        ArgUtil.notNull(beanDefinition, "beanDefinition");

        if (beanDefinition.isLazyInit()) {
            return false;
        }
        List<ConstructorArgDefinition> argDefinitionList = beanDefinition.getConstructorArgList();
        if (CollectionUtil.isNotEmpty(argDefinitionList)) {
            // 判断是否存在依赖
            for (ConstructorArgDefinition argDefinition : argDefinitionList) {
                String ref = argDefinition.getRef();
                if (StringUtil.isNotEmpty(ref)) {
                    Object instance = this.beanMap.get(ref);
                    if (ObjectUtil.isNull(instance)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * 注册单例且渴望初期初始化的对象
     * （1）如果是 singleton & lazy-init=false 则进行初始化处理
     * （2）创建完成后，对象放入 {@link #beanMap} 中，便于后期使用
     * （3）
     *
     * @param beanName       bean 名称
     * @param beanDefinition 对象定义
     * @since 0.0.3
     */
    private Object registerSingletonBean(final String beanName, final BeanDefinition beanDefinition) {
        // 单例的流程
        Object bean = beanMap.get(beanName);
        if (ObjectUtil.isNotNull(bean)) {
            // 这里直接返回的是单例，如果用户指定为多例，则每次都需要新建。
            return bean;
        }

        // 直接创建 bean
        Object newBean = createBean(beanDefinition);
        // 这里可以添加对应的监听器
        beanMap.put(beanName, newBean);
        return newBean;
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
            return this.createBean(beanDefinition);
        }

        // 单例的流程
        return this.registerSingletonBean(beanName, beanDefinition);
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
    private Object createBean(final BeanDefinition beanDefinition) {
        //1. 初始化相关处理
        final String beanName = beanDefinition.getName();
        //1.1 检测是否存在循环依赖
        if (dependsCheckService.isCircleRef(beanName)) {
            throw new IocRuntimeException(beanName + " has circle reference.");
        }

        //1.2 创建实例信息
        Object instance = DefaultNewInstanceBean.getInstance()
                .newInstance(this, beanDefinition);

        //2. 初始化完成之后的调用
        InitializingBean initializingBean = new DefaultPostConstructBean(instance, beanDefinition);
        initializingBean.initialize();
        //2.1 将初始化的信息加入列表中，便于后期销毁使用
        Pair<Object, BeanDefinition> pair = Pair.of(instance, beanDefinition);
        instanceBeanDefinitionList.add(pair);

        //3. 通知监听者
        this.notifyAllBeanCreateAware(beanName, instance);
        return instance;
    }

    /**
     * 通知所有对象创建监听器
     *
     * @param name     名称
     * @param instance 实例
     * @since 0.0.8
     */
    protected void notifyAllBeanCreateAware(final String name, final Object instance) {
    }

    @Override
    public void destroy() {
        // 销毁所有的属性信息
        // 这里的销毁严格来说，还有很多事情要做。
        // 比如销毁的顺序：最外层没有依赖的开始销毁，依次往里，直到销毁全部。可能会出现循环依赖，类似于 GC。
        for (Pair<Object, BeanDefinition> entry : instanceBeanDefinitionList) {
            DisposableBean disposableBean = new DefaultPreDestroyBean(entry.getValueOne(), entry.getValueTwo());
            disposableBean.destroy();
        }
    }

}
