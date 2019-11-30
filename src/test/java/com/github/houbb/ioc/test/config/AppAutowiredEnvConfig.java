package com.github.houbb.ioc.test.config;

import com.github.houbb.ioc.annotation.Autowired;
import com.github.houbb.ioc.annotation.Configuration;
import com.github.houbb.ioc.annotation.Import;
import com.github.houbb.ioc.support.envrionment.Environment;
import com.github.houbb.ioc.test.service.WeightApple;

/**
 * <p> project: ioc-AppConfig </p>
 * <p> create on 2019/11/19 23:01 </p>
 *
 * @author Administrator
 * @since 0.1.9
 */
@Configuration
public class AppAutowiredEnvConfig {

    @Autowired
    private Environment environment;

    /**
     * 展示环境信息
     * @since 0.1.9
     * @return 环境信息
     */
    public String[] getProfiles() {
        return environment.getActiveProfiles();
    }

}
