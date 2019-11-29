package com.github.houbb.ioc.support.condition;

/**
 * 条件接口
 * @author binbin.hou
 * @since 0.1.8
 */
public interface Condition {

    /**
     * 是否匹配
     * @param context 上下文
     * @return 结果是否匹配
     * @since 0.1.8
     */
    boolean matches(final ConditionContext context);

}
