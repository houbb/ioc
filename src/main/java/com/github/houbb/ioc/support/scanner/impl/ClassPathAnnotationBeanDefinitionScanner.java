package com.github.houbb.ioc.support.scanner.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.reflect.meta.annotation.IAnnotationTypeMeta;
import com.github.houbb.heaven.reflect.meta.annotation.impl.ClassAnnotationTypeMeta;
import com.github.houbb.heaven.support.handler.IHandler;
import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.heaven.support.metadata.util.PackageUtil;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.ioc.annotation.Component;
import com.github.houbb.ioc.annotation.Configuration;
import com.github.houbb.ioc.annotation.Primary;
import com.github.houbb.ioc.constant.AnnotationConst;
import com.github.houbb.ioc.constant.enums.BeanSourceTypeEnum;
import com.github.houbb.ioc.model.AnnotationBeanDefinition;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.model.impl.DefaultAnnotationBeanDefinition;
import com.github.houbb.ioc.support.annotation.Lazys;
import com.github.houbb.ioc.support.annotation.Scopes;
import com.github.houbb.ioc.support.name.BeanNameStrategy;
import com.github.houbb.ioc.support.scanner.AnnotationBeanDefinitionScanner;
import com.github.houbb.ioc.support.scanner.BeanDefinitionScannerContext;
import com.github.houbb.ioc.support.scanner.TypeFilter;

import java.util.List;
import java.util.Set;

/**
 * <p> project: ioc-BeanDefinitionScanner </p>
 * <p> create on 2019/11/18 20:38 </p>
 *
 * @author Administrator
 * @see com.github.houbb.ioc.annotation.Configuration 这个作为扫描的入口配置类
 * @see com.github.houbb.ioc.annotation.Component 这个作为组件类
 * @see com.github.houbb.ioc.context.AnnotationApplicationContext 注解应用上下文
 * @since 0.1.11
 */
@ThreadSafe
public class ClassPathAnnotationBeanDefinitionScanner implements AnnotationBeanDefinitionScanner {

    /**
     * 扫描指定的包，返回对应的 {@link BeanDefinition} 信息集合
     *
     * 核心流程：
     * （1）获取该包下所有的类名称
     * （2）通过类名称加载为类信息
     * （3）添加 {@link com.github.houbb.ioc.support.scanner.TypeFilter} 过滤信息
     * （4）构建 {@link AnnotationBeanDefinition} 定义对象
     *
     * @param scannerContext 包名称数组
     * @return 结果列表
     * @see com.github.houbb.ioc.model.AnnotationBeanDefinition 对象定义
     * @since 0.1.11
     */
    @Override
    public Set<AnnotationBeanDefinition> scan(final BeanDefinitionScannerContext scannerContext) {
        ArgUtil.notNull(scannerContext, "context");

        Set<AnnotationBeanDefinition> definitionSet = Guavas.newHashSet();
        Set<String> scanClassNameSet = Guavas.newHashSet();

        //1. 获取所有类全称
        List<String> packageNames = scannerContext.getScanPackages();
        for (String packageName : packageNames) {
            Set<String> classNameSet = PackageUtil.scanPackageClassNameSet(packageName);
            scanClassNameSet.addAll(classNameSet);
        }

        //2. 构建类信息
        List<Class> classList = CollectionUtil.toList(scanClassNameSet, new IHandler<String, Class>() {
            @Override
            public Class handle(String s) {
                return ClassUtil.getClass(s);
            }
        });

        //3. 添加过滤
        BeanNameStrategy beanNameStrategy = ClassUtil.newInstance(scannerContext.getBeanNameStrategy());
        for(Class clazz : classList) {
            final IAnnotationTypeMeta typeMeta = new ClassAnnotationTypeMeta(clazz);
            // 3.1 过滤符合指定注解的类
            if(isTypeFilterMatch(scannerContext, clazz, typeMeta)) {
                // 3.2 这里简单点，构建对象定义
                AnnotationBeanDefinition beanDefinition = buildComponentBeanDefinition(clazz, typeMeta, beanNameStrategy);
                definitionSet.add(beanDefinition);
            }
        }

        return definitionSet;
    }

    /**
     * 构建扫描对应的信息
     * TODO: 所有关于 {@link AnnotationBeanDefinition} 的构建应该进行进一步的抽象。
     * @return 结果
     * @since 0.1.11
     */
    private AnnotationBeanDefinition buildComponentBeanDefinition(final Class clazz,
                                                                  final IAnnotationTypeMeta typeMeta,
                                                                  final BeanNameStrategy beanNameStrategy) {
        AnnotationBeanDefinition beanDefinition = new DefaultAnnotationBeanDefinition();
        beanDefinition.setClassName(clazz.getName());
        beanDefinition.setLazyInit(Lazys.getLazy(clazz));
        beanDefinition.setScope(Scopes.getScope(clazz));
        beanDefinition.setBeanSourceType(BeanSourceTypeEnum.COMPONENT);
        String beanName = (String) typeMeta.getAnnotationOrRefAttribute(Component.class.getName(),
                AnnotationConst.METHOD_VALUE);
        if(StringUtil.isEmpty(beanName)) {
            beanName = beanNameStrategy.generateBeanName(beanDefinition);
        }
        beanDefinition.setName(beanName);

        // 指定为优先级类
        if(clazz.isAnnotationPresent(Primary.class)) {
            beanDefinition.setPrimary(true);
        }
        return beanDefinition;
    }

    /**
     * 是否类型过滤匹配
     * （1）如果是配置类，直接跳过
     * （2）如果是其他类，则需要满足扫描条件
     *
     * TODO: 这里的配置类是否可以也被自动扫描发现，最好还是按照指定的方式，避免混乱。
     * 如果需要自动发现，可以将 {@link Configuration} 相关的处理抽象为和 {@link com.github.houbb.ioc.annotation.Component}
     * 类似的处理逻辑。
     *
     * @param scannerContext 扫描上下文
     * @param clazz 类信息
     * @param typeMeta 类型元数据
     * @return 是否匹配
     * @since 0.1.11
     */
    private boolean isTypeFilterMatch(final BeanDefinitionScannerContext scannerContext,
                                      final Class clazz,
                                      final IAnnotationTypeMeta typeMeta) {
        if(clazz.isAnnotationPresent(Configuration.class)) {
            return false;
        }

        List<Class> includes = scannerContext.getIncludes();
        List<Class> excludes = scannerContext.getExcludes();
        TypeFilter typeFilter = Instances.singleton(AnnotatedTypeFilter.class);

        return typeFilter.matches(clazz, typeMeta, includes)
                && !typeFilter.matches(clazz, typeMeta, excludes);
    }

}
