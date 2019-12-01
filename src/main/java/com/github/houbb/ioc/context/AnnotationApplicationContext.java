package com.github.houbb.ioc.context;

import com.github.houbb.heaven.reflect.meta.annotation.IAnnotationTypeMeta;
import com.github.houbb.heaven.reflect.meta.annotation.impl.ClassAnnotationTypeMeta;
import com.github.houbb.heaven.reflect.meta.annotation.impl.MethodAnnotationTypeMeta;
import com.github.houbb.heaven.support.handler.IHandler;
import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.io.StreamUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectAnnotationUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectMethodUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.Optional;
import com.github.houbb.ioc.annotation.*;
import com.github.houbb.ioc.constant.enums.BeanSourceTypeEnum;
import com.github.houbb.ioc.exception.IocRuntimeException;
import com.github.houbb.ioc.model.AnnotationBeanDefinition;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.model.PropertyArgDefinition;
import com.github.houbb.ioc.model.impl.DefaultAnnotationBeanDefinition;
import com.github.houbb.ioc.model.impl.DefaultPropertyArgDefinition;
import com.github.houbb.ioc.support.annotation.Lazys;
import com.github.houbb.ioc.support.annotation.Scopes;
import com.github.houbb.ioc.support.condition.Condition;
import com.github.houbb.ioc.support.condition.impl.DefaultConditionContext;
import com.github.houbb.ioc.support.envrionment.ConfigurableEnvironment;
import com.github.houbb.ioc.support.envrionment.Environment;
import com.github.houbb.ioc.support.envrionment.PropertyResolver;
import com.github.houbb.ioc.support.envrionment.PropertySource;
import com.github.houbb.ioc.support.envrionment.impl.DefaultEnvironment;
import com.github.houbb.ioc.support.envrionment.impl.PropertiesPropertySource;
import com.github.houbb.ioc.support.envrionment.impl.PropertySourcesPropertyResolver;
import com.github.houbb.ioc.support.name.BeanNameStrategy;
import com.github.houbb.ioc.support.name.impl.DefaultBeanNameStrategy;
import com.github.houbb.ioc.support.scanner.AnnotationBeanDefinitionScanner;
import com.github.houbb.ioc.support.scanner.BeanDefinitionScannerContext;
import com.github.houbb.ioc.support.scanner.impl.ClassPathAnnotationBeanDefinitionScanner;
import com.github.houbb.ioc.support.scanner.impl.DefaultBeanDefinitionScannerContext;
import com.sun.org.apache.bcel.internal.util.ClassPath;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
    private static final Map<String, String> PRIMARY_TYPE_MAP = Guavas.newHashMap();

    /**
     * 环境信息
     * @since 0.1.9
     */
    private final ConfigurableEnvironment environment;

    public AnnotationApplicationContext(ConfigurableEnvironment environment, Class... configClasses) {
        ArgUtil.notEmpty(configClasses, "configClasses");
        ArgUtil.notNull(environment, "environment");

        this.environment = environment;
        this.configClasses = configClasses;

        super.init();
    }

    public AnnotationApplicationContext(Class... configClasses) {
        this(DefaultEnvironment.defaultInstance(), configClasses);
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
    public Environment getEnvironment() {
        return environment;
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
        Set<BeanDefinition> beanDefinitionSet = Guavas.newHashSet();

        final List<Class> configList = getConfigClassList();

        for(Class clazz : configList) {
            Optional<AnnotationBeanDefinition> configurationOpt = buildConfigurationBeanDefinition(clazz);
            if(configurationOpt.isPresent()) {
                BeanDefinition configuration = configurationOpt.get();
                beanDefinitionSet.add(configuration);

                // 配置类定义对象
                List<AnnotationBeanDefinition> beanDefinitions = buildBeanAnnotationList(configuration, clazz);
                beanDefinitionSet.addAll(beanDefinitions);

                // 扫描对象
                Set<AnnotationBeanDefinition> scanBeanDefinitions = buildScanBeanDefinitionSet(clazz);
                beanDefinitionSet.addAll(scanBeanDefinitions);
            }
        }

        // 注册 primary 对应的对象信息
        for(BeanDefinition beanDefinition : beanDefinitionSet) {
            AnnotationBeanDefinition annotationBeanDefinition = (AnnotationBeanDefinition)beanDefinition;
            if(annotationBeanDefinition.isPrimary()) {
                PRIMARY_TYPE_MAP.put(annotationBeanDefinition.getClassName(), annotationBeanDefinition.getName());
            }
        }

        // 转换为列表
        return Guavas.newArrayList(beanDefinitionSet);
    }

    /**
     * 构建扫描对象集合
     * @param clazz 类
     * @return 集合
     * @since 0.1.11
     */
    private Set<AnnotationBeanDefinition> buildScanBeanDefinitionSet(final Class clazz) {
        Set<AnnotationBeanDefinition> beanDefinitionSet =  Guavas.newHashSet();
        if(!clazz.isAnnotationPresent(ComponentScan.class)) {
            return beanDefinitionSet;
        }

        ComponentScan componentScan = (ComponentScan) clazz.getAnnotation(ComponentScan.class);

        final AnnotationBeanDefinitionScanner beanDefinitionScanner = Instances.singleton(ClassPathAnnotationBeanDefinitionScanner.class);
        DefaultBeanDefinitionScannerContext context = new DefaultBeanDefinitionScannerContext();
        context.setScanPackages(ArrayUtil.toList(componentScan.value()));
        context.setBeanNameStrategy(componentScan.beanNameStrategy());
        context.setExcludes(ArrayUtil.toList(componentScan.excludes(), new IHandler<Class<? extends Annotation>, Class>() {
            @Override
            public Class handle(Class<? extends Annotation> aClass) {
                return aClass;
            }
        }));
        context.setIncludes(ArrayUtil.toList(componentScan.includes(), new IHandler<Class<? extends Annotation>, Class>() {
            @Override
            public Class handle(Class<? extends Annotation> aClass) {
                return aClass;
            }
        }));

        return beanDefinitionScanner.scan(context);
    }

    /**
     * 构建资源属性
     * （1）这个应该每个对象独立，将这个属性放在 {@link Configuration} 标注的对象中。
     * （2）结合 {@link com.github.houbb.ioc.support.envrionment.impl.PropertySourcesPropertyResolver} 处理
     * {@link Value} 注解标注的信息。
     * （3）类似于自动注入，处理相关的信息。
     * 这个处理的实际应该比较靠前，在 {@link Configuration} 类初始化的之后，立刻进行值得处理。
     *
     * @param clazz 类信息
     * @return 结果列表
     * @since 0.1.10
     */
    private List<PropertySource> buildPropertySourceList(final Class clazz) {
        List<PropertySource> resultList = Guavas.newArrayList();
        if(!clazz.isAnnotationPresent(PropertiesResource.class)) {
            return resultList;
        }

        PropertiesResource propertiesResource = (PropertiesResource) clazz.getAnnotation(PropertiesResource.class);
        String[] strings = propertiesResource.value();

        if(ArrayUtil.isEmpty(strings)) {
            return resultList;
        }

        return ArrayUtil.toList(strings, new IHandler<String, PropertySource>() {
            @Override
            public PropertySource handle(String s) {
                Properties properties = getProperties(s);
                return new PropertiesPropertySource(s, properties);
            }
        });
    }

    /**
     * 构建参数信息
     * （1）根据 {@link #buildPropertySourceList(Class)} 设置结果
     * （2）用于后期属性设置
     * @param clazz 类
     * @return 结果列表
     * @since 0.1.10
     */
    private List<PropertyArgDefinition> buildConfigPropertyArgDefinitions(final Class clazz) {
        List<PropertyArgDefinition> resultList = Guavas.newArrayList();

        List<PropertySource> propertySourceList = buildPropertySourceList(clazz);
        PropertyResolver propertyResolver = new PropertySourcesPropertyResolver(propertySourceList);

        List<Field> fieldList = ClassUtil.getModifyableFieldList(clazz);
        for(Field field : fieldList) {
            if(field.isAnnotationPresent(Value.class)) {
                Value value = field.getAnnotation(Value.class);
                String expression = value.value();
                String actualValue = propertyResolver.resolveRequiredPlaceholders(expression);

                PropertyArgDefinition argDefinition = new DefaultPropertyArgDefinition();
                argDefinition.setName(field.getName());
                argDefinition.setFieldBase(true);
                argDefinition.setType(field.getType().getName());
                argDefinition.setValue(actualValue);

                resultList.add(argDefinition);
            }
        }

        return resultList;
    }

    /**
     * 加载配置文件信息
     * @param path 路径
     * @return 结果
     * @since 0.1.10
     */
    private Properties getProperties(final String path) {
        try(InputStream inputStream = StreamUtil.getInputStream(path)){
            Properties properties = new Properties();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            properties.load(inputStreamReader);
            return properties;
        } catch (IOException e) {
            throw new IocRuntimeException("Load properties file fail of path: " + path);
        }
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

        // 指定 conditional 验证，不符合条件直接返回。
        IAnnotationTypeMeta classTypeMeta = new ClassAnnotationTypeMeta(clazz);
        if(!conditionalMatches(classTypeMeta)) {
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

        // 设置属性配置信息
        List<PropertyArgDefinition> propertyArgDefinitions = buildConfigPropertyArgDefinitions(clazz);
        beanDefinition.setPropertyArgList(propertyArgDefinitions);

        return Optional.of(beanDefinition);
    }

    /**
     * 类级别是否匹配
     * （1）获取所有 {@link Conditional} 相关注解
     * （2）获取注解对应的 {@link Conditional#value()} 对应的类信息
     * （3）获取当前注解对应的属性信息 {@link IAnnotationTypeMeta#getAnnotationAttributes(String)}
     *
     * @param typeMeta 注解原始信息
     * @return 是否匹配
     * @since 0.1.8
     */
    private boolean conditionalMatches(IAnnotationTypeMeta typeMeta) {
        Map<Class<? extends Condition>, Map<String, Object>> map = Guavas.newHashMap();
        final String conditionalClassName = Conditional.class.getName();

        //1. 直接获取注解信息
        if(typeMeta.isAnnotated(conditionalClassName)) {
            Conditional conditional = (Conditional) typeMeta.getAnnotation(conditionalClassName);
            map.put(conditional.value(), null);
        }

        //2. 获取拓展引用的注解信息
        List<Annotation> conditionalAnnotations = typeMeta.getAnnotationRefs(conditionalClassName);
        for(Annotation annotation : conditionalAnnotations) {
            Map<String, Object> attributes = ReflectAnnotationUtil.getAnnotationAttributes(annotation);
            final String annotationTypeName = annotation.annotationType().getName();
            Conditional conditionalReferenced = (Conditional) typeMeta.getAnnotationReferenced(conditionalClassName, annotationTypeName);
            map.put(conditionalReferenced.value(), attributes);
        }

        //3. 循环处理
        DefaultConditionContext conditionContext = new DefaultConditionContext();
        conditionContext.setBeanFactory(this);
        conditionContext.setBeanDefinitionRegistry(beanDefinitionRegistry);
        conditionContext.setAnnotationTypeMeta(typeMeta);
        conditionContext.setEnvironment(this.environment);

        for(Map.Entry<Class<? extends Condition>, Map<String, Object>> entry : map.entrySet()) {
            Condition condition = ClassUtil.newInstance(entry.getKey());
            boolean match = condition.matches(conditionContext, entry.getValue());
            if(!match) {
                return false;
            }
        }

        return true;
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
     *
     * 【条件验证】
     *
     * 方法级别添加类验证 {@link Conditional}，如果不符合则直接跳过该类。
     * 如果该类没有对应的注解，不用关心 class 级别注解。
     * 因为：
     * （1）若类指定的条件不符合，不会走到方法级别。整个类都不会进行处理
     * （2）若类级别指定的条件符合，则肯定为 true。
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
                //1. 方法级别注解判定，如果不符合条件，则直接跳过。
                IAnnotationTypeMeta methodTypeMeta = new MethodAnnotationTypeMeta(method);
                if(!conditionalMatches(methodTypeMeta)) {
                    continue;
                }

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
