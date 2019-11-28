package com.github.houbb.ioc.test.config;

import com.github.houbb.ioc.annotation.Bean;
import com.github.houbb.ioc.annotation.Configuration;
import com.github.houbb.ioc.annotation.Primary;
import com.github.houbb.ioc.test.model.Book;
import com.github.houbb.ioc.test.service.ColorApple;
import com.github.houbb.ioc.test.service.WeightApple;

/**
 * <p> project: ioc-AppConfig </p>
 * <p> create on 2019/11/19 23:01 </p>
 *
 * @author Administrator
 * @since 0.1.6
 */
@Configuration
public class AppMultiBeanConfig {

    /**
     * @since 0.1.7 指定优先级
     * @return 书籍信息
     */
    @Bean
    @Primary
    public Book book() {
        return new Book("good");
    }

    @Bean
    public Book book2() {
        return new Book("bad");
    }

}
