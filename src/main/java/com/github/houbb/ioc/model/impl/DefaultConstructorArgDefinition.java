package com.github.houbb.ioc.model.impl;

import com.github.houbb.ioc.model.ConstructorArgDefinition;

/**
 * 构造器参数定义信息
 * @author binbin.hou
 * @since 0.0.6
 */
public class DefaultConstructorArgDefinition implements ConstructorArgDefinition {

    /**
     * 类型
     * @since 0.0.6
     */
    private String type;

    /**
     * 参数值
     * @since 0.0.6
     */
    private String value;

    /**
     * 引用名称
     * @since 0.0.6
     */
    private String ref;

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getRef() {
        return ref;
    }

    @Override
    public void setRef(String ref) {
        this.ref = ref;
    }
}
