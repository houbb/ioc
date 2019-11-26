package com.github.houbb.ioc.test.config;

import com.github.houbb.ioc.annotation.Bean;
import com.github.houbb.ioc.annotation.Configuration;
import com.github.houbb.ioc.annotation.Lazy;
import com.github.houbb.ioc.annotation.Scope;
import com.github.houbb.ioc.constant.ScopeConst;
import com.github.houbb.ioc.test.service.Apple;
import com.github.houbb.ioc.test.service.WeightApple;

/**
 * <p> project: ioc-AppConfig </p>
 * <p> create on 2019/11/19 23:01 </p>
 *
 * @author Administrator
 * @since 0.1.3
 */
@Configuration
public class AppBeanLazyScopeConfig {

    @Bean
    public WeightApple weightApple() {
        return new WeightApple();
    }

    /**
     * 设置为懒加载多例
     * @return 结果
     * @since 0.1.3
     */
    @Bean
    @Scope(ScopeConst.PROTOTYPE)
    @Lazy(true)
    public Apple apple() {
        return new Apple();
    }

}
