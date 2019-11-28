package com.github.houbb.ioc.context;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectMethodUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.Optional;
import com.github.houbb.ioc.annotation.Bean;
import com.github.houbb.ioc.annotation.Configuration;
import com.github.houbb.ioc.annotation.Import;
import com.github.houbb.ioc.annotation.Primary;
import com.github.houbb.ioc.constant.enums.BeanSourceTypeEnum;
import com.github.houbb.ioc.model.AnnotationBeanDefinition;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.model.impl.DefaultAnnotationBeanDefinition;
import com.github.houbb.ioc.support.annotation.Lazys;
import com.github.houbb.ioc.support.annotation.Scopes;
import com.github.houbb.ioc.support.name.BeanNameStrategy;
import com.github.houbb.ioc.support.name.impl.DefaultBeanNameStrategy;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
     * 主要类型映射 map
     * @since 0.1.7
     */
    private Map<String, String> PRIMARY_TYPE_MAP = Guavas.newHashMap();

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

    @Override
    protected <T> T getPrimaryBean(Class<T> requiredType) {
        String typeName = requiredType.getName();
        if(PRIMARY_TYPE_MAP.containsKey(typeName)) {
            String beanName = PRIMARY_TYPE_MAP.get(typeName);
            return super.getBean(beanName, requiredType);
        }

        return null;
    }

    /**
     * 构建对象属性列表
     * @return 对象属性列表
     * @since 0.1.1
     */
    @Override
    protected List<BeanDefinition> buildBeanDefinitionList() {
        List<BeanDefinition> resultList = Guavas.newArrayList();

        final List<Class> configList = getConfigClassList();
        for(Class clazz : configList) {
            Optional<AnnotationBeanDefinition> configurationOpt = buildConfigurationBeanDefinition(clazz);
            if(configurationOpt.isPresent()) {
                BeanDefinition configuration = configurationOpt.get();
                resultList.add(configuration);

                List<AnnotationBeanDefinition> beanDefinitions = buildBeanAnnotationList(configuration, clazz);
                resultList.addAll(beanDefinitions);
            }
        }

        // 注册 primary 对应的对象信息
        for(BeanDefinition beanDefinition : resultList) {
            AnnotationBeanDefinition annotationBeanDefinition = (AnnotationBeanDefinition)beanDefinition;
            if(annotationBeanDefinition.isPrimary()) {
                PRIMARY_TYPE_MAP.put(annotationBeanDefinition.getClassName(), annotationBeanDefinition.getName());
            }
        }

        return resultList;
    }



    /**
     * 获取配置类列表信息
     * （1）这里首先需要进行一层过滤，避免重复导入。
     * （2）这里需要递归处理，处理 class 头上的 {@link com.github.houbb.ioc.annotation.Import} 配置信息。
     *
     * @return 配置类列表
     * @since 0.1.4
     */
    private List<Class> getConfigClassList() {
        Set<Class> configSet = Guavas.newHashSet(this.configClasses.length);

        for(Class configClass : configClasses) {
            addAllImportClass(configSet, configClass);
        }

        return Guavas.newArrayList(configSet);
    }

    /**
     * 添加所有导入配置信息
     * @param configSet 配置集合
     * @param configClass 配置类
     * @since 0.1.4
     */
    private void addAllImportClass(final Set<Class> configSet, final Class configClass) {
        configSet.add(configClass);

        if(configClass.isAnnotationPresent(Import.class)) {
            Import importInfo = (Import) configClass.getAnnotation(Import.class);

            Class[] importClasses = importInfo.value();
            if(ArrayUtil.isNotEmpty(importClasses)) {
                for(Class importClass : importClasses) {
                    //1. 循环添加
                    configSet.add(importClass);
                    //2. 递归处理
                    addAllImportClass(configSet, importClass);
                }
            }
        }
    }

    /**
     * 构建配置对象定义
     * @param clazz 类信息
     * @return 属性定义
     * @since 0.1.2
     */
    private Optional<AnnotationBeanDefinition> buildConfigurationBeanDefinition(final Class clazz) {
        if(!clazz.isAnnotationPresent(Configuration.class)) {
            return Optional.empty();
        }

        Configuration configuration = (Configuration) clazz.getAnnotation(Configuration.class);
        String beanName = configuration.value();

        AnnotationBeanDefinition beanDefinition = new DefaultAnnotationBeanDefinition();
        beanDefinition.setClassName(clazz.getName());
        beanDefinition.setLazyInit(Lazys.getLazy(clazz));
        beanDefinition.setScope(Scopes.getScope(clazz));
        beanDefinition.setBeanSourceType(BeanSourceTypeEnum.CONFIGURATION);
        if(StringUtil.isEmpty(beanName)) {
            beanName = beanNameStrategy.generateBeanName(beanDefinition);
        }
        beanDefinition.setName(beanName);

        // 指定为优先级类
        if(clazz.isAnnotationPresent(Primary.class)) {
            beanDefinition.setPrimary(true);
        }

        return Optional.of(beanDefinition);
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
     * @param configuration 对象配置定义信息
     * @param clazz 类型信息
     * @return 结果列表
     * @since 0.1.2
     */
    private List<AnnotationBeanDefinition> buildBeanAnnotationList(final BeanDefinition configuration, final Class clazz) {
        ArgUtil.notNull(clazz, "clazz");
        if(!clazz.isAnnotationPresent(Configuration.class)) {
            return Collections.emptyList();
        }

        List<AnnotationBeanDefinition> resultList = Guavas.newArrayList();
        List<Method> methodList = ClassUtil.getMethodList(clazz);
        for(Method method : methodList) {
            if(method.isAnnotationPresent(Bean.class)) {
                Bean bean = method.getAnnotation(Bean.class);
                String methodName = method.getName();
                Class<?> returnType = method.getReturnType();
                String beanName = bean.value();
                if(StringUtil.isEmpty(beanName)) {
                    beanName = methodName;
                }

                AnnotationBeanDefinition beanDefinition = new DefaultAnnotationBeanDefinition();
                beanDefinition.setName(beanName);
                beanDefinition.setClassName(returnType.getName());
                beanDefinition.setInitialize(bean.initMethod());
                beanDefinition.setDestroy(bean.destroyMethod());
                // 如何获取这个实例，反倒是可以直接通过方法调用。
                beanDefinition.setBeanSourceType(BeanSourceTypeEnum.CONFIGURATION_BEAN);
                beanDefinition.setConfigurationName(configuration.getName());
                beanDefinition.setConfigurationBeanMethod(methodName);
                beanDefinition.setConfigBeanMethodParamTypes(method.getParameterTypes());
                beanDefinition.setConfigBeanMethodParamRefs(ReflectMethodUtil.getParamNames(method));

                beanDefinition.setLazyInit(Lazys.getLazy(method));
                beanDefinition.setScope(Scopes.getScope(method));

                // 设置为优先
                if(method.isAnnotationPresent(Primary.class)) {
                    beanDefinition.setPrimary(true);
                }

                resultList.add(beanDefinition);
            }
        }
        return resultList;
    }

}
