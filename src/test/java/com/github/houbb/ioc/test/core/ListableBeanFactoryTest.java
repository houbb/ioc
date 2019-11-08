package com.github.houbb.ioc.test.core;

import com.github.houbb.ioc.context.JsonApplicationContext;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.test.service.Apple;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
public class ListableBeanFactoryTest {

    /**
     * bean 工厂
     * @since 0.0.2
     */
    private static final BeanFactory BEAN_FACTORY = new JsonApplicationContext("apple.json");


    /**
     * 测试
     * @since 0.0.2
     */
    @Test
    public void getBeanByNameTest() {
        Apple apple = (Apple) BEAN_FACTORY.getBean("apple");
        apple.color();
    }

    /**
     * 测试
     * @since 0.0.2
     */
    @Test
    public void getBeanByNameTypeTest() {
        Apple apple = BEAN_FACTORY.getBean("apple", Apple.class);
        apple.color();
    }

    /**
     * 测试
     * @since 0.0.2
     */
    @Test
    public void containsBeanTest() {
        Assert.assertTrue(BEAN_FACTORY.containsBean("apple"));
        Assert.assertFalse(BEAN_FACTORY.containsBean("box"));
    }

    /**
     * 测试
     * @since 0.0.2
     */
    @Test
    public void isTypeMatchTest() {
        Assert.assertTrue(BEAN_FACTORY.isTypeMatch("apple", Apple.class));
        Assert.assertFalse(BEAN_FACTORY.isTypeMatch("apple", BeanFactory.class));
    }

}
