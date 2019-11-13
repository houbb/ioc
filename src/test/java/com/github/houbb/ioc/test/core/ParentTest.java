package com.github.houbb.ioc.test.core;

import com.github.houbb.ioc.context.JsonApplicationContext;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.test.model.User;
import com.github.houbb.ioc.test.service.Apple;
import org.junit.Assert;
import org.junit.Test;

/**
 * 继承测试
 * @author binbin.hou
 * @since 0.0.9
 */
public class ParentTest {

    @Test
    public void parentNameTest() {
        final BeanFactory beanFactory = new JsonApplicationContext("parent/user-parent.json");
        User copyUser = beanFactory.getBean("copyUser", User.class);
        Assert.assertEquals("helen", copyUser.getName());
    }

}
