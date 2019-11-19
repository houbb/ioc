package com.github.houbb.ioc.test.config;

import com.github.houbb.ioc.annotation.Configuration;

/**
 * <p> project: ioc-AppConfig </p>
 * <p> create on 2019/11/19 23:01 </p>
 *
 * @author Administrator
 * @since 0.1.1
 */
@Configuration
public class AppConfig {

    public String name() {
        return "app config";
    }

}
