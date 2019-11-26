package com.github.houbb.ioc.test.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * <p> project: ioc-Apple </p>
 * <p> create on 2019/11/6 20:02 </p>
 *
 * @author Administrator
 * @since 0.1.2
 */
public class WeightApple {

    public void init() {
        System.out.println("WeightApple init");
    }

    public void destroy() {
        System.out.println("WeightApple destroy");
    }

    /**
     * 获取重量配置
     * @return 重量
     * @since 0.1.2
     */
    public String getWeight() {
        return "10";
    }

}
