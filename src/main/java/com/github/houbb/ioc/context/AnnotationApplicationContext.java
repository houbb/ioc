package com.github.houbb.ioc.context;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.ioc.annotation.Configuration;
import com.github.houbb.ioc.constant.enums.ScopeEnum;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.model.impl.DefaultBeanDefinition;
import com.github.houbb.ioc.support.name.BeanNameStrategy;
import com.github.houbb.ioc.support.name.impl.DefaultBeanNameStrategy;
import com.github.houbb.json.bs.JsonBs;

import java.io.InputStream;
import java.util.List;

/**
 * 注解应用上下文
 *
 * @author binbin.hou
 * @since 0.1.1
 * @see com.github.houbb.ioc.annotation.Configuration 注解信息
 */
public class AnnotationApplicationContext extends AbstractApplicationContext {

    /**
     * 配置类信息
     * @since 0.1.1
     */
    private final Class[] configClasses;

    /**
     * 命名策略
     * @since 0.1.1
     */
    private BeanNameStrategy beanNameStrategy = Instances.singleton(DefaultBeanNameStrategy.class);

    public AnnotationApplicationContext(Class... configClasses) {
        ArgUtil.notEmpty(configClasses, "configClasses");
        this.configClasses = configClasses;

        super.init();
    }

    /**
     * 设置属性名称命名策略
     * @param beanNameStrategy 命名策略
     * @since 0.1.1
     */
    public void setBeanNameStrategy(BeanNameStrategy beanNameStrategy) {
        this.beanNameStrategy = beanNameStrategy;
    }

    /**
     * 构建对象属性列表
     * @return 对象属性列表
     * @since 0.1.1
     */
    @Override
    protected List<BeanDefinition> buildBeanDefinitionList() {
        List<BeanDefinition> beanDefinitionList = Guavas.newArrayList();

        for(Class clazz : configClasses) {
            if(clazz.isAnnotationPresent(Configuration.class)) {
                Configuration configuration = (Configuration) clazz.getAnnotation(Configuration.class);
                String beanName = configuration.value();

                BeanDefinition beanDefinition = DefaultBeanDefinition.newInstance();
                beanDefinition.setClassName(clazz.getName());
                beanDefinition.setLazyInit(false);
                beanDefinition.setScope(ScopeEnum.SINGLETON.getCode());
                if(StringUtil.isEmpty(beanName)) {
                    beanName = beanNameStrategy.generateBeanName(beanDefinition);
                }
                beanDefinition.setName(beanName);
                beanDefinitionList.add(beanDefinition);
            }
        }

        return beanDefinitionList;
    }

}
