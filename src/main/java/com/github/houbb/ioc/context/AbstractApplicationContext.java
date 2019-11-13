package com.github.houbb.ioc.context;

import com.github.houbb.heaven.support.handler.IHandler;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.MapUtil;
import com.github.houbb.ioc.constant.enums.ScopeEnum;
import com.github.houbb.ioc.core.impl.DefaultListableBeanFactory;
import com.github.houbb.ioc.exception.IocRuntimeException;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.model.PropertyArgDefinition;
import com.github.houbb.ioc.support.aware.ApplicationContextAware;
import com.github.houbb.ioc.support.cycle.DependsCheckService;
import com.github.houbb.ioc.support.cycle.impl.DefaultDependsCheckService;
import com.github.houbb.ioc.support.processor.ApplicationContextPostProcessor;

import java.util.HashMap;
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
     * 全部的对象定义 map
     * @since 0.0.9
     */
    private Map<String, BeanDefinition> beanDefinitionMap = Guavas.newHashMap();

    /**
     * 子对象定义类表
     * @since 0.0.9
     */
    private List<BeanDefinition> childBeanDefinitionList = Guavas.newArrayList();

    /**
     * 抽象的对象信息列表
     * @since 0.0.9
     */
    private List<BeanDefinition> abstractDefinitionList = Guavas.newArrayList();

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
        List<BeanDefinition> beanDefinitionList = buildBeanDefinitionList();

        // 这里对 bean 进行统一的处理
        this.buildCreateAbleBeanDefinitionList(beanDefinitionList);

        // 允许用户自定义的部分。
        createAbleDefinitionList = this.postProcessor(createAbleDefinitionList);

        // 注册所有的依赖检测
        super.getDependsCheckService().registerBeanDefinitions(createAbleDefinitionList);

        // 基本属性的设置注册
        this.registerBeanDefinitions(createAbleDefinitionList);

        // 注册钩子函数
        this.registerShutdownHook();

        this.notifyAllAware();
    }

    /**
     * 对象定义列表
     * （1）将 abstract 类型的 beanDefine 区分开。
     * （2）对 parent 指定的 beanDefine 进行属性赋值处理。
     * @param beanDefinitionList 所有的对象列表
     * @since 0.0.9
     */
    void buildCreateAbleBeanDefinitionList(final List<BeanDefinition> beanDefinitionList) {
        // 特殊类型首先进行处理
        for(BeanDefinition beanDefinition : beanDefinitionList) {
            final String name = beanDefinition.getName();
            final String parentName = beanDefinition.getParentName();

            beanDefinitionMap.put(name, beanDefinition);

            if(beanDefinition.isAbstractClass()) {
                abstractDefinitionList.add(beanDefinition);
            } else if(StringUtil.isNotEmpty(parentName)) {
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

            BeanDefinition parentDefinition = beanDefinitionMap.get(parentName);
            if(ObjectUtil.isNull(parentDefinition)) {
                throw new IocRuntimeException(parentName +" not found !");
            }

            BeanDefinition newChild = buildChildBeanDefinition(child, parentDefinition);
            // 设置所有子类不为 null 的属性。
            newChild.setName(name);

            createAbleDefinitionList.add(newChild);
        }
    }

    /**
     * 构建新的子类属性定义
     * 注意：
     * （1）为了简化，只继承 property 属性信息。
     * （2）父类的属性，子类必须全部都有，否则就会报错，暂时不做优化处理。
     *
     * 核心流程：
     *
     * （1）获取 child 中的所有 property 属性
     * （2）获取 parent 中所有的 property 属性
     * （3）进行过滤处理，相同的以  child 为准。
     *
     * @param child 子类
     * @param parent 父类
     * @return 属性定义
     * @since 0.0.9
     */
    private BeanDefinition buildChildBeanDefinition(final BeanDefinition child,
                                                    final BeanDefinition parent) {
        List<PropertyArgDefinition> childList = Guavas.newArrayList(child.getPropertyArgList());

        Map<String, PropertyArgDefinition> childArgsMap = MapUtil.toMap(child.getPropertyArgList(), new IHandler<PropertyArgDefinition, String>() {
            @Override
            public String handle(PropertyArgDefinition propertyArgDefinition) {
                return propertyArgDefinition.getName();
            }
        });

        List<PropertyArgDefinition> parentArgs = parent.getPropertyArgList();
        if(CollectionUtil.isNotEmpty(parentArgs)) {
            for(PropertyArgDefinition parentArg : parentArgs) {
                String name = parentArg.getName();
                if(childArgsMap.containsKey(name)) {
                    continue;
                }

                // 不包含的，则进行添加
                childList.add(parentArg);
            }
        }

        child.setPropertyArgList(childList);
        return child;
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
