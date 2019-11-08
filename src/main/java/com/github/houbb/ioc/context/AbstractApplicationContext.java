package com.github.houbb.ioc.context;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.ioc.constant.enums.ScopeEnum;
import com.github.houbb.ioc.core.impl.DefaultListableBeanFactory;
import com.github.houbb.ioc.model.BeanDefinition;

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

        this.registerBeanDefinitions(beanDefinitions);

        this.registerShutdownHook();
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
