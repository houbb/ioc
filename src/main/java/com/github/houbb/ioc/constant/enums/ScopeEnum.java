package com.github.houbb.ioc.constant.enums;

/**
 * <p> project: ioc-ScopeEnum </p>
 * <p> create on 2019/11/8 20:19 </p>
 *
 * @author Administrator
 * @since 0.0.3
 */
public enum ScopeEnum {
    /**
     * 单例
     * @since 0.0.3
     */
    SINGLETON("singleton"),

    /**
     * 多例
     */
    PROTOTYPE("prototype"),
    ;

    /**
     * 编码信息
     * @since 0.0.3
     */
    private final String code;

    ScopeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
