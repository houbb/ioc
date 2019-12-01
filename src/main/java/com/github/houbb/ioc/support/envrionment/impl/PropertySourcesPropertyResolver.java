package com.github.houbb.ioc.support.envrionment.impl;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.ioc.exception.IocRuntimeException;
import com.github.houbb.ioc.support.envrionment.ConfigurablePropertyResolver;
import com.github.houbb.ioc.support.envrionment.PropertySource;

import java.util.List;

/**
 * <p> project: ioc-PropertySourcePropertyResolver </p>
 * <p> create on 2019/11/30 21:45 </p>
 *
 * @author Administrator
 * @see PropertySource 配置资源信息
 * @since 0.1.10
 */
public class PropertySourcesPropertyResolver implements ConfigurablePropertyResolver {

    /**
     * 前缀
     * @since 0.1.10
     */
    private String placeholderPrefix = "${";

    /**
     * 后缀
     * @since 0.1.10
     */
    private String placeholderSuffix = "}";

    /**
     * 分隔符
     * @since 0.1.10
     */
    private String valueSeparator = ":";

    /**
     * 可遍历的资源信息
     * @since 0.1.10
     */
    private List<PropertySource> propertySourceList = Guavas.newArrayList();

    public PropertySourcesPropertyResolver(List<PropertySource> propertySourceList) {
        ArgUtil.notNull(propertySourceList, "propertySourceList");

        this.propertySourceList = propertySourceList;
    }

    @Override
    public void setPlaceholderPrefix(String placeholderPrefix) {
        this.placeholderPrefix = placeholderPrefix;
    }

    @Override
    public void setPlaceholderSuffix(String placeholderSuffix) {
        this.placeholderSuffix = placeholderSuffix;
    }

    @Override
    public void setValueSeparator(String valueSeparator) {
        this.valueSeparator = valueSeparator;
    }

    @Override
    public boolean containsProperty(String key) {
        for(PropertySource propertySource : propertySourceList) {
            if(propertySource.containsProperty(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getProperty(String key) {
        return getProperty(key, null);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        for(PropertySource propertySource : propertySourceList) {
            if(propertySource.containsProperty(key)) {
                return (String) propertySource.getProperty(key);
            }
        }
        return defaultValue;
    }

    @Override
    public String getRequiredProperty(String key) {
        String value = getProperty(key);
        if(ObjectUtil.isNull(value)) {
            throw new IocRuntimeException("Can't resolve property for key " + key);
        }
        return value;
    }

    @Override
    public String resolvePlaceholders(String text) {
        ArgUtil.notEmpty(text, "text");

        return resolvePlaceholder(text, false);
    }

    @Override
    public String resolveRequiredPlaceholders(String text) {
        ArgUtil.notEmpty(text, "text");

        return resolvePlaceholder(text, true);
    }

    private String resolvePlaceholder(final String text, final boolean required) {
        if(text.startsWith(placeholderPrefix) && text.endsWith(placeholderSuffix)) {
            String placeholder = text.substring(2, text.length()-1);

            String[] strings = placeholder.split(valueSeparator);
            String key = strings[0];

            if(strings.length == 1) {
                // 断言必须存在
                return getRequiredProperty(key);
            }

            // 第一个为键，第二个为默认值
            String defaultValue = strings[1];
            return getProperty(key, defaultValue);
        }

        return text;
    }

}
