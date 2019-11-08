package com.github.houbb.ioc.test.context;

import com.github.houbb.ioc.context.JsonApplicationContext;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.test.service.Apple;
import org.junit.Test;

/**
 * <p> project: ioc-JsonApplicationContextTest </p>
 * <p> create on 2019/11/6 20:09 </p>
 *
 * @author Administrator
 * @since 0.0.1
 */
@Deprecated
public class JsonApplicationContextTest {

    @Test
    public void simpleTest() {
        BeanFactory beanFactory = new JsonApplicationContext("apple.json");
        Apple apple = beanFactory.getBean("apple", Apple.class);
        apple.color();
    }

}
