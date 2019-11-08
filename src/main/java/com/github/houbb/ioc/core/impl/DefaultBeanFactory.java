package com.github.houbb.ioc.core.impl;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.ioc.constant.enums.ScopeEnum;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.exception.IocRuntimeException;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.util.ClassUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * bean 工厂接口
 * @author binbin.hou
 * @since 0.0.1
 */
public class DefaultBeanFactory implements BeanFactory {

    /**
     * 对象信息 map
     * @since 0.0.1
     */
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * 对象 map
     * @since 0.0.1
     */
    private Map<String, Object> beanMap = new ConcurrentHashMap<>();

    /**
     * 类型集合
     * （1）主要是为了 type 类型，获取对应的信息为做准备
     * （2）考虑到懒加载的处理。
     * @see #getBean(String, Class) 获取对应对象信息
     * @since 0.0.2
     */
    private Map<Class, Set<String>> typeBeanNameMap = new ConcurrentHashMap<>();

    /**
     * 注册对象定义信息
     * @since 0.0.1
     */
    protected void registerBeanDefinition(final String beanName, final BeanDefinition beanDefinition) {
        ArgUtil.notEmpty(beanName, "beanName");
        ArgUtil.notNull(beanDefinition, "beanDefinition");

        // 这里可以添加监听器
        this.beanDefinitionMap.put(beanName, beanDefinition);

        //2. 注册类型和 beanNames 信息
        this.registerTypeBeanNames(beanName, beanDefinition);

        //3. 初始化 bean 信息
        final boolean isLazyInit = beanDefinition.isLazyInit();
        if(!isLazyInit) {
            this.registerSingletonBean(beanName, beanDefinition);
        }
    }

    /**
     * 注册类型和 beanNames 信息
     * @param beanName 单个 bean 名称
     * @param beanDefinition 对象定义
     * @since 0.0.2
     */
    private void registerTypeBeanNames(final String beanName, final BeanDefinition beanDefinition) {
        final Class type = getType(beanDefinition);
        Set<String> beanNameSet = typeBeanNameMap.get(type);
        if(ObjectUtil.isNull(beanNameSet)) {
            beanNameSet = Guavas.newHashSet();
        }
        beanNameSet.add(beanName);
        typeBeanNameMap.put(type, beanNameSet);
    }

    /**
     * 注册单例且渴望初期初始化的对象
     * （1）如果是 singleton & lazy-init=false 则进行初始化处理
     * （2）创建完成后，对象放入 {@link #beanMap} 中，便于后期使用
     * （3）
     * @param beanName bean 名称
     * @param beanDefinition 对象定义
     * @since 0.0.3
     */
    private Object registerSingletonBean(final String beanName, final BeanDefinition beanDefinition) {
        // 单例的流程
        Object bean = beanMap.get(beanName);
        if(ObjectUtil.isNotNull(bean)) {
            // 这里直接返回的是单例，如果用户指定为多例，则每次都需要新建。
            return bean;
        }

        // 直接创建 bean
        Object newBean = createBean(beanDefinition);
        // 这里可以添加对应的监听器
        beanMap.put(beanName, newBean);
        return newBean;
    }


    /**
     * 根据类型获取对应的属性名称
     * @param requiredType 需求类型
     * @return bean 名称列表
     * @since 0.0.2
     */
    Set<String> getBeanNames(final Class requiredType) {
        ArgUtil.notNull(requiredType, "requiredType");
        return typeBeanNameMap.get(requiredType);
    }

    @Override
    public Object getBean(String beanName) {
        ArgUtil.notNull(beanName, "beanName");
        // 获取对应配置信息
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(ObjectUtil.isNull(beanDefinition)) {
            throw new IocRuntimeException(beanName + " not exists in bean define.");
        }

        // 如果为多例，直接创建新的对象即可。
        final String scope = beanDefinition.getScope();
        if(!ScopeEnum.SINGLETON.getCode().equals(scope)) {
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
        return (T)object;
    }

    @Override
    public boolean containsBean(String beanName) {
        ArgUtil.notNull(beanName, "beanName");

        return beanDefinitionMap.containsKey(beanName);
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
     * @param beanDefinition 对象定义信息
     * @return 创建的对象信息
     * @since 0.0.1
     */
    private Object createBean(final BeanDefinition beanDefinition) {
        String className = beanDefinition.getClassName();
        Class clazz = ClassUtils.getClass(className);
        return ClassUtils.newInstance(clazz);
    }

    /**
     * 获取类型信息
     * （1）这里主要是一种
     * @param beanDefinition 对象属性
     * @return 对应的 bean 信息
     * @since 0.0.2
     */
    private Class getType(final BeanDefinition beanDefinition) {
        String className = beanDefinition.getClassName();
        return ClassUtils.getClass(className);
    }

}
