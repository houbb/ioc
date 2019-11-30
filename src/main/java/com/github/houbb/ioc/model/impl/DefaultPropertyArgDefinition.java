package com.github.houbb.ioc.model.impl;

import com.github.houbb.ioc.model.PropertyArgDefinition;

/**
 * 默认属性属性参数
 * 
 * @author binbin.hou
 * @since 0.0.7
 */
public class DefaultPropertyArgDefinition implements PropertyArgDefinition {

    /**
     * 属性名称
     * @since 0.0.7
     */
    private String name;

    /**
     * 类型
     * @since 0.0.7
     */
    private String type;

    /**
     * 参数值
     * @since 0.0.7
     */
    private String value;

    /**
     * 引用名称
     * @since 0.0.7
     */
    private String ref;

    /**
     * 是否基于字段
     * @since 0.1.10
     */
    private boolean fieldBase;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

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

    @Override
    public boolean isFieldBase() {
        return fieldBase;
    }

    @Override
    public void setFieldBase(boolean fieldBase) {
        this.fieldBase = fieldBase;
    }

    @Override
    public String toString() {
        return "DefaultPropertyArgDefinition{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", ref='" + ref + '\'' +
                ", fieldBase=" + fieldBase +
                '}';
    }

}
