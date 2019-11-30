package com.github.houbb.ioc.support.condition;

import java.util.Map;

/**
 * 条件接口
 * 注意：都应该提供无参数构造器
 * @author binbin.hou
 * @since 0.1.8
 */
public interface Condition {

    /**
     * 是否匹配
     * @param context 上下文
     * @param attributes 注解相关信息
     * @return 结果是否匹配
     * @since 0.1.8
     */
    boolean matches(final ConditionContext context, final Map<String, Object> attributes);

}
