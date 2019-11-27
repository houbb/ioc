package com.github.houbb.ioc.test.config;

import com.github.houbb.ioc.annotation.Bean;
import com.github.houbb.ioc.annotation.Configuration;
import com.github.houbb.ioc.test.model.Book;
import com.github.houbb.ioc.test.model.User;
import com.github.houbb.ioc.test.service.WeightApple;

/**
 * <p> project: ioc-AppConfig </p>
 * <p> create on 2019/11/19 23:01 </p>
 *
 * @author Administrator
 * @since 0.1.5
 */
@Configuration
public class AppBeanRefConfig {

    @Bean
    public Book book() {
        Book book = new Book();
        book.setName("《海底两万里》");
        return book;
    }

    @Bean
    public User user(final Book book) {
        User user = new User();
        user.setName("Hello");
        user.setBook(book);
        return user;
    }

}
