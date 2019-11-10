package com.github.houbb.ioc.support.converter;

import com.github.houbb.json.bs.JsonBs;

/**
 * 字符串转换工厂类
 * @author binbin.hou
 * @since 0.0.6
 */
public final class StringValueConverterFactory {

    private StringValueConverterFactory(){}

    /**
     * 获取转换后的值
     * @param string 字符串
     * @param clazz 类型
     * @return 结果
     * @since 0.0.6
     */
    public static Object convertValue(final String string,
                                      final Class clazz) {
        return JsonBs.deserialize(string, clazz);
    }

}
