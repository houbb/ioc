package com.github.houbb.ioc.context;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.ioc.constant.enums.ScopeEnum;
import com.github.houbb.ioc.core.impl.DefaultListableBeanFactory;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.support.aware.ApplicationContextAware;
import com.github.houbb.ioc.support.processor.ApplicationContextPostProcessor;

import java.util.List;

/**
 * <p> project: ioc-AbstractApplicationContext </p>
 * <p> create on 2019/11/8 22:27 </p>
 *
 * @author Administrator
 * @since 0.0.4
 */
public abstract class AbstractApplicationContext extends DefaultListableBeanFactory implements ApplicationContext {

    @Override
    public String getApplicationName() {
        return "application context";
    }

    protected void init() {
        List<? extends BeanDefinition> beanDefinitions = buildBeanDefinitionList();

        beanDefinitions = this.postProcessor(beanDefinitions);
        this.registerBeanDefinitions(beanDefinitions);

        this.registerShutdownHook();

        this.notifyAllAware();
    }

    /**
     * 循环执行 bean 信息处理
     * @param beanDefinitions 对象基本信息
     * @return 结果列表
     * @since 0.0.8
     */
    private List<? extends BeanDefinition> postProcessor(List<? extends BeanDefinition> beanDefinitions) {
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
    protected abstract List<? extends BeanDefinition> buildBeanDefinitionList();

    /**
     * 注册对象属性列表
     * @param beanDefinitionList 对象属性列表
     * @since 0.0.4
     */
    protected void registerBeanDefinitions(final List<? extends BeanDefinition> beanDefinitionList) {
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
