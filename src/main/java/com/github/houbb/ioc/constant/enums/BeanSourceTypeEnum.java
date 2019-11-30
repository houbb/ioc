package com.github.houbb.ioc.constant.enums;

/**
 * 对象资源类型枚举
 * @author binbin.hou
 * @since 0.1.2
 */
public enum BeanSourceTypeEnum {

    /**
     * 通过资源文件配置
     * @since 0.1.2
     */
    RESOURCE,

    /**
     * 配置注解类
     * @since 0.1.2
     */
    CONFIGURATION,

    /**
     * 配置注解 bean 类
     * @since 0.1.2
     */
    CONFIGURATION_BEAN,

    /**
     * 拓展支持类
     * @since 0.1.9
     */
    SUPPORT
    ;

    /**
     * 是否为配置对象
     * @param sourceTypeEnum 数据类型枚举
     * @return 结果
     * @since 0.1.5
     */
    public static boolean isConfigurationBean(final BeanSourceTypeEnum sourceTypeEnum) {
        return CONFIGURATION_BEAN.equals(sourceTypeEnum);
    }

}
