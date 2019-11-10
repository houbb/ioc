package com.github.houbb.ioc.model;

/**
 * 属性参数定义
 * 
 * @author binbin.hou
 * @since 0.0.7
 */
public interface PropertyArgDefinition {

    /**
     * 属性名称
     * @return 属性名称
     * @since 0.0.7
     */
    String getName();

    /**
     * 设置属性名称
     * @param name 属性名称
     * @since 0.0.7
     */
    void setName(final String name);

    /**
     * 参数类型全称
     * @return 类型全称
     * @since 0.0.7
     */
    String getType();

    /**
     * 设置参数类型全称
     * @param type 参数类型全称
     * @since 0.0.7
     */
    void setType(final String type);

    /**
     * 值信息
     * @return 值信息
     * @since 0.0.7
     */
    String getValue();

    /**
     * 设置值
     * @param value 值
     * @since 0.0.7
     */
    void setValue(final String value);

    /**
     * 获取引用的名称
     * @return 引用的例子名称
     * @since 0.0.7
     */
    String getRef();

    /**
     * 设置引用属性名称
     * @param ref 引用属性名称
     * @since 0.0.7
     */
    void setRef(final String ref);

}
