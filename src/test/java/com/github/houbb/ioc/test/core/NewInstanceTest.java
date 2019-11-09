package com.github.houbb.ioc.test.core;

import com.github.houbb.ioc.context.JsonApplicationContext;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.test.service.Apple;
import com.github.houbb.ioc.test.service.ColorApple;
import com.github.houbb.ioc.test.service.ColorWeightApple;
import org.junit.Test;

/**
 * @author binbin.hou
 * @since 0.0.6
 */
public class NewInstanceTest {

    /**
     * 测试
     * @since 0.0.6
     */
    @Test
    public void factoryMethodAnnotationTest() throws InterruptedException {
        BeanFactory beanFactory = new JsonApplicationContext("create/colorApple.json");
        ColorApple apple = beanFactory.getBean("apple", ColorApple.class);
        System.out.println(apple);
    }

    /**
     * 构造器测试
     * @since 0.0.6
     */
    @Test
    public void constructorTest() {
        BeanFactory beanFactory = new JsonApplicationContext("create/colorApple.json");
        ColorWeightApple apple = beanFactory.getBean("weightApple", ColorWeightApple.class);
        System.out.println(apple);
    }

}
