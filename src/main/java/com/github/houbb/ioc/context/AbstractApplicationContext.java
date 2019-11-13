package com.github.houbb.ioc.context;

import com.github.houbb.bean.mapping.core.util.BeanUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.ioc.constant.enums.ScopeEnum;
import com.github.houbb.ioc.core.impl.DefaultListableBeanFactory;
import com.github.houbb.ioc.exception.IocRuntimeException;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.support.aware.ApplicationContextAware;
import com.github.houbb.ioc.support.processor.ApplicationContextPostProcessor;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> project: ioc-AbstractApplicationContext </p>
 * <p> create on 2019/11/8 22:27 </p>
 *
 * @author Administrator
 * @since 0.0.4
 */
public abstract class AbstractApplicationContext extends DefaultListableBeanFactory implements ApplicationContext {

    /**
     * 抽象定义 map
     * @since 0.0.9
     */
    private Map<String, BeanDefinition> abstractDefinitionMap = new IdentityHashMap<>();

    /**
     * 子对象定义类表
     * @since 0.0.9
     */
    private List<BeanDefinition> childBeanDefinitionList = Guavas.newArrayList();

    /**
     * 所有的对象信息列表
     * @since 0.0.9
     */
    private List<BeanDefinition> beanDefinitionList = Guavas.newArrayList();

    /**
     * 可创建的定义列表信息
     * @since 0.0.9
     */
    private List<BeanDefinition> createAbleDefinitionList = Guavas.newArrayList();

    @Override
    public String getApplicationName() {
        return "application context";
    }

    /**
     * 初始上下文
     * @since 0.0.9
     */
    protected void init() {
        // 原始数据信息
        beanDefinitionList = buildBeanDefinitionList();

        // 这里对 bean 进行统一的处理
        this.buildCreateAbleBeanDefinitionList();

        // 允许用户自定义的部分。
        createAbleDefinitionList = this.postProcessor(createAbleDefinitionList);

        // 基本属性的设置
        this.registerBeanDefinitions(createAbleDefinitionList);

        this.registerShutdownHook();

        this.notifyAllAware();
    }

    /**
     * 对象定义列表
     * （1）将 abstract 类型的 beanDefine 区分开。
     * （2）对 parent 指定的 beanDefine 进行属性赋值处理。
     * @since 0.0.9
     */
    void buildCreateAbleBeanDefinitionList() {
        // 特殊类型首先进行处理
        for(BeanDefinition beanDefinition : beanDefinitionList) {
            final String name = beanDefinition.getName();
            final String parentName = beanDefinition.getParentName();
            if(beanDefinition.isAbstractClass()) {
                abstractDefinitionMap.put(name, beanDefinition);
            } else if(StringUtil.isEmpty(parentName)) {
                childBeanDefinitionList.add(beanDefinition);
            } else {
                createAbleDefinitionList.add(beanDefinition);
            }
        }

        // 重新统一构建
        this.addAllChildBeanDefinition();
    }

    /**
     * 构建子节点定义列表
     * @since 0.0.9
     */
    private void addAllChildBeanDefinition() {
        for(BeanDefinition child : childBeanDefinitionList) {
            final String name = child.getName();
            final String parentName = child.getParentName();
            if(StringUtil.isEmpty(name)) {
                throw new IocRuntimeException("name can not be empty!");
            }
            if(name.equals(parentName)) {
                throw new IocRuntimeException(name + " parent bean is ref to itself!");
            }

            BeanDefinition abstractDefinition = abstractDefinitionMap.get(parentName);
            if(ObjectUtil.isNull(abstractDefinition)) {
                throw new IocRuntimeException(parentName +" not found !");
            }

            BeanDefinition newChild = abstractDefinition.clone();
            BeanUtil.copyProperties(child, newChild);
            newChild.setAbstractClass(false);
            createAbleDefinitionList.add(newChild);
        }
    }

    /**
     * 循环执行 bean 信息处理
     * @param beanDefinitions 对象基本信息
     * @return 结果列表
     * @since 0.0.8
     */
    private List<BeanDefinition> postProcessor(List<BeanDefinition> beanDefinitions) {
        List<ApplicationContextPostProcessor> postProcessors = super.getBeans(ApplicationContextPostProcessor.class);

        for(ApplicationContextPostProcessor processor : postProcessors) {
            beanDefinitions = processor.beforeRegister(beanDefinitions);
        }
        return beanDefinitions;
    }

    /**
     * 注册处理所有的 {@link com.github.houbb.ioc.support.aware.ApplicationContextAware} 监听器
     * @since 0.0.8
     */
    private void notifyAllAware() {
        List<ApplicationContextAware> awareList = super.getBeans(ApplicationContextAware.class);

        for(ApplicationContextAware aware : awareList) {
            aware.setApplicationContext(this);
        }
    }

    /**
     * 构建 bean 属性定义列表
     * @return 属性定义列表
     * @since 0.0.4
     */
    protected abstract List<BeanDefinition> buildBeanDefinitionList();

    /**
     * 注册对象属性列表
     * @param beanDefinitionList 对象属性列表
     * @since 0.0.4
     */
    protected void registerBeanDefinitions(final List<BeanDefinition> beanDefinitionList) {
        if(CollectionUtil.isNotEmpty(beanDefinitionList)) {
            for (BeanDefinition beanDefinition : beanDefinitionList) {
                // 填充默认值
                this.fillDefaultValue(beanDefinition);

                super.registerBeanDefinition(beanDefinition.getName(), beanDefinition);
            }
        }
    }

    /**
     * 填充默认值信息
     * @param beanDefinition 对象属性定义
     * @since 0.0.4
     */
    private void fillDefaultValue(BeanDefinition beanDefinition) {
        String scope = beanDefinition.getScope();
        if(StringUtil.isEmpty(scope)) {
            beanDefinition.setScope(ScopeEnum.SINGLETON.getCode());
        }
    }

    /**
     * 注册关闭钩子函数
     * @since 0.0.4
     */
    protected void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                AbstractApplicationContext.this.destroy();
            }
        });
    }

}
