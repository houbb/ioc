package com.github.houbb.ioc.test.core;

import com.github.houbb.ioc.context.JsonApplicationContext;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.exception.IocRuntimeException;
import com.github.houbb.ioc.test.model.User;
import com.github.houbb.ioc.test.service.ColorWeightApple;
import org.junit.Assert;
import org.junit.Test;

/**
 * 循环依赖测试
 * @author binbin.hou
 * @since 0.1.0
 */
public class CircleTest {

    /**
     * a-b 直接互相依赖
     * @since 0.1.0
     */
    @Test(expected = IocRuntimeException.class)
    public void directCircleTest() {
        final BeanFactory beanFactory = new JsonApplicationContext("circle/direct-circle.json");
        ColorWeightApple apple = beanFactory.getBean("weightApple", ColorWeightApple.class);
    }

    /**
     * a-b
     * b-c
     * c-a
     *
     * 间接循环依赖
     * @since 0.1.0
     */
    @Test(expected = IocRuntimeException.class)
    public void inDirectCircleTest() {
        final BeanFactory beanFactory = new JsonApplicationContext("circle/in-direct-circle.json");
        ColorWeightApple apple = beanFactory.getBean("weightApple", ColorWeightApple.class);
    }

}
