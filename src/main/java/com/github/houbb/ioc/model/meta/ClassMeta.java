package com.github.houbb.ioc.model.meta;

import com.github.houbb.heaven.annotation.CommonEager;

import java.util.List;

/**
 * 类元数据
 * @author binbin.hou
 * @since 0.1.6
 */
@CommonEager
public interface ClassMeta extends BaseMeta {

    /**
     * 获取字段元信息列表
     * @return 字段列表
     * @since 0.1.6
     */
    List<FieldMeta> getFieldMetas();

    /**
     * 设置字段元信息
     * @param fieldMetas 字段元信息
     * @since 0.1.6
     */
    void setFieldMetas(final List<FieldMeta> fieldMetas);

    /**
     * 获取方法元信息
     * @return 方法元信息
     * @since 0.1.6
     */
    List<MethodMeta> getMethodMetas();

    /**
     * 设置方法元信息
     * @param methodMetas 方法元信息
     * @since 0.1.6
     */
    void setMethodMetas(final List<MethodMeta> methodMetas);

}
