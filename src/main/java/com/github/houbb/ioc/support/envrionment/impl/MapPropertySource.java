package com.github.houbb.ioc.support.envrionment.impl;

import com.github.houbb.ioc.support.envrionment.PropertySource;

import java.util.Map;

/**
 * <p> project: ioc-MapPropertySource </p>
 * <p> create on 2019/11/30 21:55 </p>
 *
 * @author Administrator
 * @since 0.1.10
 */
public class MapPropertySource implements PropertySource<Map<String, Object>> {

    /**
     * 资源名称
     * @since 0.1.10
     */
    private String name;

    /**
     * 资源信息
     * @since 0.1.10
     */
    private Map<String, Object> source;

    public MapPropertySource(String name, Map<String, Object> source) {
        this.name = name;
        this.source = source;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean containsProperty(String name) {
        return source.containsKey(name);
    }

    @Override
    public Object getProperty(String name) {
        return source.get(name);
    }

}
