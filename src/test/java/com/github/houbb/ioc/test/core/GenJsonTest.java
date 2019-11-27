package com.github.houbb.ioc.test.core;

import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.model.ConstructorArgDefinition;
import com.github.houbb.ioc.model.impl.DefaultBeanDefinition;
import com.github.houbb.ioc.model.impl.DefaultConstructorArgDefinition;
import com.github.houbb.json.bs.JsonBs;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author binbin.hou
 * @since 0.0.6
 */
public class GenJsonTest {

    /**
     * 生成 apple json 测试
     * @since 0.0.1
     */
    @Test
    @Ignore
    public void genAppleJsonTest() {
        List<BeanDefinition> beanDefinitions = Guavas.newArrayList();
        BeanDefinition weightApple = new DefaultBeanDefinition();
        weightApple.setName("weightApple");
        weightApple.setClassName("com.github.houbb.ioc.test.service.ColorWeightApple");

        ConstructorArgDefinition argRef = new DefaultConstructorArgDefinition();
        argRef.setRef("apple");

        ConstructorArgDefinition argWeight = new DefaultConstructorArgDefinition();
        argWeight.setType("java.lang.Integer");
        argWeight.setValue("10");

        weightApple.setConstructorArgList(Arrays.asList(argRef, argWeight));
        beanDefinitions.add(weightApple);

        BeanDefinition apple = new DefaultBeanDefinition();
        apple.setName("apple");
        apple.setClassName("com.github.houbb.ioc.test.service.ColorApple");
        beanDefinitions.add(apple);

        String json = JsonBs.serialize(beanDefinitions);
        System.out.println(json);

        System.out.println("===========================");

        System.out.println(JsonBs.deserializeArray(json, BeanDefinition.class));
    }

}
