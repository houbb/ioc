package com.github.houbb.ioc.constant.enums;

import com.github.houbb.ioc.constant.ScopeConst;

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
    SINGLETON(ScopeConst.SINGLETON),

    /**
     * 多例
     * @since 0.0.3
     */
    PROTOTYPE(ScopeConst.PROTOTYPE),
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
