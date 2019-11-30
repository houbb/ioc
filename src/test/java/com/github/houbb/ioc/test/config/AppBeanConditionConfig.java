package com.github.houbb.ioc.test.config;

import com.github.houbb.ioc.annotation.Bean;
import com.github.houbb.ioc.annotation.Conditional;
import com.github.houbb.ioc.annotation.Configuration;
import com.github.houbb.ioc.annotation.Primary;
import com.github.houbb.ioc.test.model.Book;
import com.github.houbb.ioc.test.support.condition.FalseCondition;
import com.github.houbb.ioc.test.support.condition.TrueCondition;

/**
 * <p> project: ioc-AppConfig </p>
 * <p> create on 2019/11/19 23:01 </p>
 *
 * @author Administrator
 * @since 0.1.8
 */
@Configuration
public class AppBeanConditionConfig {

    @Bean
    @Conditional(FalseCondition.class)
    public Book book() {
        return new Book("good");
    }

    @Bean
    @Conditional(TrueCondition.class)
    public Book book2() {
        return new Book("bad");
    }

}
