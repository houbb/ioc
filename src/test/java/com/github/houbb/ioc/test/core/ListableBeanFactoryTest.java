package com.github.houbb.ioc.test.core;

import com.github.houbb.ioc.context.JsonApplicationContext;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.core.ListableBeanFactory;
import com.github.houbb.ioc.core.impl.DefaultBeanFactory;
import com.github.houbb.ioc.test.service.Apple;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
public class ListableBeanFactoryTest {

    /**
     * bean 工厂
     * @since 0.0.2
     */
    private static final ListableBeanFactory BEAN_FACTORY = new JsonApplicationContext("apples.json");

    /**
     * 测试
     * @since 0.0.2
     */
    @Test
    public void getBeansTest() {
        List<Apple> appleList = BEAN_FACTORY.getBeans(Apple.class);
        appleList.get(0).color();
        appleList.get(1).color();
    }

}
