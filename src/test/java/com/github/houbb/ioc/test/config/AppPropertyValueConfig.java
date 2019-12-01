package com.github.houbb.ioc.test.config;

import com.github.houbb.ioc.annotation.Configuration;
import com.github.houbb.ioc.annotation.Value;

/**
 * <p> project: ioc-AppConfig </p>
 * <p> create on 2019/11/19 23:01 </p>
 *
 * @author Administrator
 * @since 0.1.10
 */
@Configuration
public class AppPropertyValueConfig {

    /**
     * 名称
     * @since 0.1.10
     */
    @Value("${name:ryo}")
    private String name;

    public String getName() {
        return this.name;
    }

}
