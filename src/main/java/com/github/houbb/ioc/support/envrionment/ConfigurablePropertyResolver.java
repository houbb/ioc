package com.github.houbb.ioc.support.envrionment;

/**
 * 可配置的属性处理类
 *
 * <p> project: ioc-PropertyResolver </p>
 * <p> create on 2019/11/30 21:30 </p>
 *
 * @author Administrator
 * @since 0.1.10
 */
public interface ConfigurablePropertyResolver extends PropertyResolver {

    /**
     * Set the prefix that placeholders replaced by this resolver must begin with.
     * @param placeholderPrefix 占位符开始
     * @since 0.1.10
     */
    void setPlaceholderPrefix(String placeholderPrefix);

    /**
     * Set the suffix that placeholders replaced by this resolver must end with.
     * @param placeholderSuffix 占位符结束
     * @since 0.1.10
     */
    void setPlaceholderSuffix(String placeholderSuffix);

    /**
     * Specify the separating character between the placeholders replaced by this
     * resolver and their associated default value, or {@code null} if no such
     * special character should be processed as a value separator.
     *
     * @param valueSeparator 占位符分割
     * @since 0.1.10
     */
    void setValueSeparator(String valueSeparator);

}
