package com.github.houbb.ioc.test.config;

import com.github.houbb.ioc.annotation.Autowired;
import com.github.houbb.ioc.annotation.Configuration;
import com.github.houbb.ioc.annotation.Import;
import com.github.houbb.ioc.test.service.WeightApple;

/**
 * <p> project: ioc-AppConfig </p>
 * <p> create on 2019/11/19 23:01 </p>
 *
 * @author Administrator
 * @since 0.1.6
 */
@Configuration
@Import(AppBeanConfig.class)
public class AppAutowiredConfig {

    @Autowired
    private WeightApple weightApple;

    /**
     * 展示重量
     * @since 0.1.6
     * @return 重量
     */
    public String getWeight() {
        return weightApple.getWeight();
    }

}
