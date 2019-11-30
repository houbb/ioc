package com.github.houbb.ioc.support.envrionment;

/**
 * <p> project: ioc-PropertyResource </p>
 * <p> create on 2019/11/30 21:48 </p>
 *
 * @author Administrator
 * @since 0.1.10
 */
public interface PropertySource<T> {

    /**
     * 获取属性资源名称
     * @return 属性资源名称
     * @since 0.1.10
     */
    String getName();

    /**
     * Return whether this {@code PropertySource} contains the given name.
     * <p>This implementation simply checks for a {@code null} return value
     * from {@link #getProperty(String)}. Subclasses may wish to implement
     * a more efficient algorithm if possible.
     * @param name the property name to find
     *
     * @return 是否包含指定属性
     * @since 0.1.10
     */
    boolean containsProperty(String name);

    /**
     * Return the value associated with the given name,
     * or {@code null} if not found.
     * @param name the property to find
     * @see PropertyResolver#getRequiredProperty(String)
     *
     * @return 对应的属性信息
     * @since 0.1.10
     */
    Object getProperty(String name);

}
