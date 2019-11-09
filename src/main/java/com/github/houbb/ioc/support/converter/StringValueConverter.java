package com.github.houbb.ioc.support.converter;

/**
 * 字符串值转换类
 * @author binbin.hou
 * @since 0.0.6
 * @param <T> 泛型
 */
public interface StringValueConverter<T> {

    /**
     * 对字符串值进行转换
     * @param value 值
     * @return 转换后的结果
     */
    T convert(final String value);

}
