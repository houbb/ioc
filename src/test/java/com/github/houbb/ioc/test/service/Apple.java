package com.github.houbb.ioc.test.service;

import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.model.impl.DefaultBeanDefinition;
import com.github.houbb.json.bs.JsonBs;

import java.util.List;

/**
 * <p> project: ioc-Apple </p>
 * <p> create on 2019/11/6 20:02 </p>
 *
 * @author Administrator
 * @since 0.0.1
 */
public class Apple {

    public void color() {
        System.out.println("Apple color: red. ");
    }

    public static void main(String[] args) {
        List<BeanDefinition> beanDefinitions = Guavas.newArrayList();
        BeanDefinition apple = new DefaultBeanDefinition();
        apple.setClassName("com.github.houbb.ioc.test.service.Apple");
        apple.setName("apple");
        beanDefinitions.add(apple);

        System.out.println(JsonBs.serialize(beanDefinitions));

        System.out.println(JsonBs.deserialize("[{\"name\":\"apple\",\"className\":\"com.github.houbb.ioc.test.service.Apple\"}]",
                beanDefinitions.getClass()));
    }

}
