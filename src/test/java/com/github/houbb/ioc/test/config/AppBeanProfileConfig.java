package com.github.houbb.ioc.test.config;

import com.github.houbb.ioc.annotation.Bean;
import com.github.houbb.ioc.annotation.Conditional;
import com.github.houbb.ioc.annotation.Configuration;
import com.github.houbb.ioc.annotation.Profile;
import com.github.houbb.ioc.constant.ProfileConst;
import com.github.houbb.ioc.support.condition.impl.ProfileCondition;
import com.github.houbb.ioc.test.model.Book;
import com.github.houbb.ioc.test.support.condition.FalseCondition;
import com.github.houbb.ioc.test.support.condition.TrueCondition;

/**
 * <p> project: ioc-AppConfig </p>
 * <p> create on 2019/11/19 23:01 </p>
 *
 * @author Administrator
 * @since 0.1.9
 */
@Configuration
@Profile({ProfileConst.DEV, ProfileConst.TEST})
public class AppBeanProfileConfig {

    @Bean
    @Profile({ProfileConst.DEV})
    public Book devBook() {
        return new Book("devBook");
    }

    @Bean
    @Profile({ProfileConst.TEST})
    public Book testBook() {
        return new Book("testBook");
    }

}
