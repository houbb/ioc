package com.github.houbb.ioc.test.support.condition;

import com.github.houbb.ioc.support.condition.Condition;
import com.github.houbb.ioc.support.condition.ConditionContext;

import java.util.Map;

/**
 * <p> project: ioc-TrueCondition </p>
 * <p> create on 2019/11/30 11:21 </p>
 *
 * @author Administrator
 * @since 0.1.8
 */
public class TrueCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, Map<String, Object> attributes) {
        return true;
    }
}
