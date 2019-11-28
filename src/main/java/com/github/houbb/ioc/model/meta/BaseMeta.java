package com.github.houbb.ioc.model.meta;

import com.github.houbb.heaven.annotation.CommonEager;

import java.util.List;

/**
 * 基础元数据
 * @author binbin.hou
 * @since 0.1.6
 */
@CommonEager
public interface BaseMeta {

    /**
     * 获取 class 信息
     * @return 类信息
     * @since 0.1.6
     */
    Class getClasss();

    /**
     * 设置 class 信息
     * @param clazz 类信息
     * @since 0.1.6
     */
    void setClass(final Class clazz);



}
