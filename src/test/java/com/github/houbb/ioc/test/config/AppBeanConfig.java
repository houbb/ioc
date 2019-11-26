package com.github.houbb.ioc.test.config;

import com.github.houbb.ioc.annotation.Bean;
import com.github.houbb.ioc.annotation.Configuration;
import com.github.houbb.ioc.test.service.Apple;
import com.github.houbb.ioc.test.service.WeightApple;

/**
 * <p> project: ioc-AppConfig </p>
 * <p> create on 2019/11/19 23:01 </p>
 *
 * @author Administrator
 * @since 0.1.2
 */
@Configuration
public class AppBeanConfig {

    @Bean
    public WeightApple weightApple() {
        return new WeightApple();
    }

}
