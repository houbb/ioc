package com.github.houbb.ioc.test.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * <p> project: ioc-Apple </p>
 * <p> create on 2019/11/6 20:02 </p>
 *
 * @author Administrator
 * @since 0.0.1
 */
public class Apple {

    @PostConstruct
    public void init() {
        System.out.println("Apple init");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Apple destroy");
    }

    public void color() {
        System.out.println("Apple color: red. ");
    }

}
