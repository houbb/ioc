package com.github.houbb.ioc.model;

import java.util.List;

/**
 * 注解对象定义属性
 * @author binbin.hou
 * @since 0.1.2
 */
public interface AnnotationBeanDefinition extends BeanDefinition {

    String setMethodName(final String methodName);

    String getMethodName();

}
