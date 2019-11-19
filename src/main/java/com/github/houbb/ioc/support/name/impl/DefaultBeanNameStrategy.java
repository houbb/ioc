package com.github.houbb.ioc.support.name.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.support.name.BeanNameStrategy;

/**
 * Bean 命名策略
 * <p> project: ioc-BeanNameStrategy </p>
 * <p> create on 2019/11/19 22:39 </p>
 *
 * @author Administrator
 * @since 0.1.1
 */
@ThreadSafe
public class DefaultBeanNameStrategy implements BeanNameStrategy {

    /**
     * 生成对象名称
     * （1）默认直接类名称首字母小写即可。
     * （2）如果已经指定 {@link BeanDefinition#getName()}，则直接返回即可。
     *
     * @param definition bean 属性定义
     * @return 生成的结果名称
     * @since 0.1.1
     */
    public String generateBeanName(BeanDefinition definition) {
        final String name = definition.getName();
        if(StringUtil.isNotEmpty(name)) {
            return name;
        }

        String className = definition.getClassName();
        ArgUtil.notEmpty(className, "className");
        String classSimpleName = className.substring(className.lastIndexOf(".")+1);
        return StringUtil.firstToLowerCase(classSimpleName);
    }

}
