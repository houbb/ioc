package com.github.houbb.ioc.support.cycle.impl;

import com.github.houbb.heaven.annotation.NotThreadSafe;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.ioc.constant.enums.BeanSourceTypeEnum;
import com.github.houbb.ioc.model.AnnotationBeanDefinition;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.model.ConstructorArgDefinition;
import com.github.houbb.ioc.model.PropertyArgDefinition;
import com.github.houbb.ioc.support.cycle.DependsCheckService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * （1）注册的时候，存入所有当前 bean 对应的所有依赖信息
 * （2）注册 bean 被哪些依赖。
 *
 * <p> project: ioc-CycleService </p>
 * <p> create on 2019/11/13 22:14 </p>
 *
 * @author Administrator
 * @since 0.1.0
 */
@NotThreadSafe
public class DefaultDependsCheckService implements DependsCheckService {

    /**
     * 对象依赖的 map
     * @since 0.1.0
     */
    private Map<String, Set<String>> beanDependsOnMap = new HashMap<>();


    /**
     * 对象被哪些属性依赖
     * @since 0.1.0
     */
    private Map<String, Set<String>> beanDependsByMap = new HashMap<>();

    @Override
    public void registerBeanDefinitions(List<BeanDefinition> beanDefinitionList) {
        for(BeanDefinition beanDefinition : beanDefinitionList) {
            final String beanName = beanDefinition.getName();

            Set<String> dependsOnSet = getDependsOnSet(beanDefinition);
            beanDependsOnMap.put(beanName, dependsOnSet);

            // 循环处理 dependsBy 集合
            this.fillDependsByMap(dependsOnSet, beanName);
        }
    }

    /**
     * 构建 dependsBy map
     * @param dependsOnSet 依赖集合
     * @param beanName 当前 beanName
     * @since 0.1.0
     */
    private void fillDependsByMap(final Set<String> dependsOnSet, final String beanName) {
        if(CollectionUtil.isEmpty(dependsOnSet)) {
            return;
        }

        for(String dependsOn : dependsOnSet) {
            Set<String> dependsBySet = beanDependsByMap.get(dependsOn);
            if(ObjectUtil.isNull(dependsBySet)) {
                dependsBySet = Guavas.newHashSet();
            }

            dependsBySet.add(beanName);
            beanDependsByMap.put(dependsOn, dependsBySet);
        }
    }

    /**
     * 获取对象的所有依赖信息
     * （1）构造器依赖 {@link ConstructorArgDefinition#getRef()}
     * （2）属性值依赖 {@link PropertyArgDefinition#getRef()}
     * （3）如果是 config-bean，则 config 首先就是 bean 对应的依赖。 @since 0.1.4
     *  {@link AnnotationBeanDefinition#getConfigBeanMethodParamRefs()} @since 0.1.5
     *
     * @param beanDefinition 对象定义
     * @return 依赖结果名称
     * @since 0.1.0
     */
    private Set<String> getDependsOnSet(final BeanDefinition beanDefinition) {
        Set<String> dependsSet = Guavas.newHashSet();

        final BeanSourceTypeEnum sourceType = beanDefinition.getBeanSourceType();
        if(BeanSourceTypeEnum.CONFIGURATION_BEAN.equals(sourceType)) {
            AnnotationBeanDefinition annotationBeanDefinition = (AnnotationBeanDefinition)beanDefinition;
            dependsSet.add(annotationBeanDefinition.getConfigurationName());

            // 方法参数
            dependsSet.addAll(annotationBeanDefinition.getConfigBeanMethodParamRefs());
        }

        List<ConstructorArgDefinition> constructorArgDefinitions = beanDefinition.getConstructorArgList();
        if(CollectionUtil.isNotEmpty(constructorArgDefinitions)) {
            for(ConstructorArgDefinition argDefinition : constructorArgDefinitions) {
                String ref = argDefinition.getRef();
                if (StringUtil.isNotEmpty(ref)) {
                    dependsSet.add(ref);
                }
            }
        }

        List<PropertyArgDefinition> propertyArgDefinitions = beanDefinition.getPropertyArgList();
        if(CollectionUtil.isNotEmpty(propertyArgDefinitions)) {
            for(PropertyArgDefinition argDefinition : propertyArgDefinitions) {
                final String ref = argDefinition.getRef();
                if(StringUtil.isNotEmpty(ref)) {
                    dependsSet.add(ref);
                }
            }
        }

        return dependsSet;
    }

    /**
     * 是否是循环依赖
     * @param beanName 对象名称
     * @return 是否依赖
     * @since 0.1.0
     */
    public boolean isCircleRef(String beanName) {
        ArgUtil.notEmpty(beanName, "beanName");

        Set<String> dependsOnSet = beanDependsOnMap.get(beanName);
        if(CollectionUtil.isEmpty(dependsOnSet)) {
            return false;
        }

        for(String dependsOn : dependsOnSet) {
            if(isCircleRef(beanName, dependsOn, null)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 递归判断是否存在循环依赖
     * @param beanName 属性名称
     * @param dependsOnBeanName 依赖的名称
     * @param alreadySeen 已经 check 的属性列表
     * @return 是否
     * @since 0.1.0
     */
    private boolean isCircleRef(String beanName, String dependsOnBeanName, Set<String> alreadySeen) {
        // 当前属性已经检查过一次了
        if(CollectionUtil.isNotEmpty(alreadySeen)
            && alreadySeen.contains(beanName)) {
            return false;
        }

        // 获取当前 beanName 被谁引用
        Set<String> dependsBySet = beanDependsByMap.get(beanName);
        if(CollectionUtil.isEmpty(dependsBySet)) {
            return false;
        }

        // 如果依赖我的，包含了我依赖的。就是一个循环依赖。
        if(dependsBySet.contains(dependsOnBeanName)) {
            return true;
        }

        // 如果不是，那么递归看引用我的属性，是否依赖我依赖的属性
        for(String dependsBy : dependsBySet) {
            if(ObjectUtil.isNull(alreadySeen)) {
                alreadySeen = Guavas.newHashSet();
            }
            alreadySeen.add(beanName);

            // 递归调用
            if(isCircleRef(dependsBy, dependsOnBeanName, alreadySeen)) {
                return true;
            }
        }

        return false;
    }

}
