package com.github.houbb.ioc.test.core;

import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.ioc.context.JsonApplicationContext;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.model.impl.DefaultBeanDefinition;
import com.github.houbb.ioc.test.service.Apple;
import com.github.houbb.json.bs.JsonBs;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public void PostConstructTest() throws InterruptedException {
        BeanFactory beanFactory = new JsonApplicationContext("apple.json");
        Apple apple = (Apple) beanFactory.getBean("apple");
    }



}
