package com.github.houbb.ioc.model.meta;

import com.github.houbb.heaven.annotation.CommonEager;

import java.util.List;

/**
 * 基础元数据
 * @author binbin.hou
 * @since 0.1.6
 */
@CommonEager
public interface BaseAnnotationMeta extends BaseMeta {

    /**
     * 获取注解信息
     * @return 注解元信息列表
     * @since 0.1.6
     */
    List<AnnotationMeta> getAnnotationMetas();

    /**
     * 设置注解元数据
     * @param annotationMetas 注解元信息
     * @since 0.1.6
     */
    void setAnnotationMetas(final List<AnnotationMeta> annotationMetas);

}
