package com.github.houbb.ioc.context;

import com.github.houbb.ioc.core.impl.DefaultBeanFactory;

/**
 * JSON 应用上下文
 * @author binbin.hou
 * @since 0.0.1
 */
public class JsonApplicationContext extends DefaultBeanFactory {

    /**
     * 文件名称
     * @since 0.0.1
     */
    private final String fileName;

    public JsonApplicationContext(String fileName) {
        this.fileName = fileName;

        // 初始化配置
    }





}
