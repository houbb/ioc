package com.github.houbb.ioc.test.core;

import com.github.houbb.ioc.context.JsonApplicationContext;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.test.service.Apple;
import org.junit.Test;

/**
 * @author binbin.hou
 * @since 0.0.4
 */
public class InitTest {

    /**
     * 测试
     * @since 0.0.4
     */
    @Test
    public void postConstructTest() throws InterruptedException {
        BeanFactory beanFactory = new JsonApplicationContext("apple.json");
        Apple apple = (Apple) beanFactory.getBean("apple");
    }

}
