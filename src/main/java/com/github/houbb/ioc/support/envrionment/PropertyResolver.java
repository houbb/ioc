package com.github.houbb.ioc.support.envrionment;

/**
 * <p> project: ioc-PropertyResolver </p>
 * <p> create on 2019/11/30 21:30 </p>
 *
 * @author Administrator
 * @since 0.1.10
 *
 * @see PropertySource 资源信息接口
 */
public interface PropertyResolver {

    /**
     * Return whether the given property key is available for resolution, i.e.,
     * the value for the given key is not {@code null}.
     * @param key 键名称
     * @since 0.1.10
     * @return 是否包含指定属性
     */
    boolean containsProperty(String key);

    /**
     * Return the property value associated with the given key, or {@code null}
     * if the key cannot be resolved.
     * @param key the property name to resolve
     * @return 对应的值
     * @since 0.1.10
     */
    String getProperty(String key);

    /**
     * Return the property value associated with the given key, or
     * {@code defaultValue} if the key cannot be resolved.
     * @param key the property name to resolve
     * @param defaultValue the default value to return if no value is found
     * @return 对应的值
     * @since 0.1.10
     */
    String getProperty(String key, String defaultValue);

    /**
     * Return the property value associated with the given key (never {@code null}).
     *
     * @param key 键
     * @return 结果
     * @since 0.1.10
     */
    String getRequiredProperty(String key);

    /**
     * Resolve ${...} placeholders in the given text, replacing them with corresponding
     * property values as resolved by {@link #getProperty}.
     *
     * Unresolvable placeholders with no default value are ignored and passed through unchanged.
     *
     * @param text the String to resolve
     * @return the resolved String (never {@code null})
     * @throws IllegalArgumentException if given text is {@code null}
     * @see #resolveRequiredPlaceholders
     */
    String resolvePlaceholders(String text);

    /**
     * Resolve ${...} placeholders in the given text, replacing them with corresponding
     * property values as resolved by {@link #getProperty}. Unresolvable placeholders with
     * no default value will cause an IllegalArgumentException to be thrown.
     * @return the resolved String (never {@code null})
     * @throws IllegalArgumentException if given text is {@code null}
     * or if any placeholders are unresolvable
     *
     * @param text 文本信息
     * @since 0.1.10
     */
    String resolveRequiredPlaceholders(String text);

}
