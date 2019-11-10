package com.github.houbb.ioc.utils;

import com.github.houbb.heaven.annotation.CommonEager;
import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author binbin.hou
 * @since 0.0.7
 */
@CommonEager
public class MethodUtils {


    /***
     * 调用 setter 方法，进行设置值
     * @since 0.0.7
     */
    public static void invokeSetterMetthod(final Object instance,
                                           final String propertyName,
                                           final Object value) {
        ArgUtil.notNull(instance, "instance");
        ArgUtil.notNull(propertyName, "propertyName");
        if(ObjectUtil.isNull(value)) {
            return;
        }

        final Class<?> clazz = instance.getClass();
        String setMethodName = "set"+ StringUtil.firstToUpperCase(propertyName);

        // 反射获取对应的方法
        final Class<?> paramType = value.getClass();
        try {
            Method method = clazz.getMethod(setMethodName, paramType);
            method.invoke(instance, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new CommonRuntimeException(e);
        }
    }

}
