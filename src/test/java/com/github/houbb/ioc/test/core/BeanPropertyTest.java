package com.github.houbb.ioc.test.core;

import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.ioc.context.JsonApplicationContext;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.model.ConstructorArgDefinition;
import com.github.houbb.ioc.model.PropertyArgDefinition;
import com.github.houbb.ioc.model.impl.DefaultBeanDefinition;
import com.github.houbb.ioc.model.impl.DefaultConstructorArgDefinition;
import com.github.houbb.ioc.model.impl.DefaultPropertyArgDefinition;
import com.github.houbb.ioc.test.model.User;
import com.github.houbb.ioc.test.service.Apple;
import com.github.houbb.json.bs.JsonBs;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author binbin.hou
 * @since 0.0.6
 */
public class BeanPropertyTest {

    /**
     * 生成 apple json 测试
     * @since 0.0.1
     */
    @Test
    public void genUserJsonTest() {
        List<BeanDefinition> beanDefinitions = Guavas.newArrayList();
        BeanDefinition book = new DefaultBeanDefinition();
        book.setName("book");
        book.setClassName("com.github.houbb.ioc.test.model.Book");
        beanDefinitions.add(book);

        BeanDefinition user = new DefaultBeanDefinition();
        user.setName("user");
        user.setClassName("com.github.houbb.ioc.test.model.User");

        PropertyArgDefinition userNameArgDefinition = new DefaultPropertyArgDefinition();
        userNameArgDefinition.setName("name");
        userNameArgDefinition.setValue("helen");
        userNameArgDefinition.setType("java.lang.String");

        PropertyArgDefinition userBookArgDefinition = new DefaultPropertyArgDefinition();
        userBookArgDefinition.setName("book");
        userBookArgDefinition.setRef("book");
        user.setPropertyArgList(Arrays.asList(userNameArgDefinition, userBookArgDefinition));
        beanDefinitions.add(user);

        String json = JsonBs.serialize(beanDefinitions);
        System.out.println(json);
    }

    /**
     * 测试
     * TODO: 这里估计是 JsonBs 存在反序列的 BUG，导致名称缺失。
     * 下一次修复。
     * @since 0.0.2
     */
    @Test
    public void beanPropertyTest() {
        BeanFactory beanFactory = new JsonApplicationContext("property/user-property.json");
        User user = beanFactory.getBean("user", User.class);
        System.out.println(user);
    }

}
