package com.github.houbb.ioc.context;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectMethodUtil;
import com.github.houbb.ioc.annotation.Bean;
import com.github.houbb.ioc.annotation.Configuration;
import com.github.houbb.ioc.constant.enums.ScopeEnum;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.model.impl.DefaultBeanDefinition;
import com.github.houbb.ioc.support.name.BeanNameStrategy;
import com.github.houbb.ioc.support.name.impl.DefaultBeanNameStrategy;
import com.github.houbb.json.bs.JsonBs;
import com.sun.org.apache.xpath.internal.Arg;

import java.io.InputStream;
import java.lang.reflect.Method;
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

                // 构建 beanList
                beanDefinitionList.add(beanDefinition);
            }
        }

        return beanDefinitionList;
    }

    /**
     * 构建 {@link com.github.houbb.ioc.annotation.Bean} 注解指定的方法对应的信息
     *
     * （1）对象的结果怎么处理？结合 Config 进行 invoke 获取吗？
     * （2）如果是 lazy-init，那么肯定需要两样东西。方法签名+对应的依赖 config-instance。
     * 这两样可以考虑放在 AnnotationBeanDefinition 中。
     * （3）beanName 应该怎么获取？根据 methodName（这个） 还是根据 className？
     * 这个逻辑可以放在 {@link BeanNameStrategy} 中，保证逻辑的统一性。
     *
     * @param clazz 类型信息
     * @return 结果列表
     * @since 0.1.2
     */
    private List<BeanDefinition> buildBeanAnnotationList(final Class clazz) {
        ArgUtil.notNull(clazz, "clazz");

        List<BeanDefinition> beanDefinitionList = Guavas.newArrayList();

        List<Method> methodList = ClassUtil.getMethodList(clazz);
        for(Method method : methodList) {
            if(method.isAnnotationPresent(Bean.class)) {
                String methodName = method.getName();
                Class<?> returnType = method.getReturnType();

                Bean bean = method.getAnnotation(Bean.class);
                String beanName = bean.value();
                if(StringUtil.isEmpty(beanName)) {
                    beanName = methodName;
                }
            }
        }
        return beanDefinitionList;
    }

}
