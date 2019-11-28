package com.github.houbb.ioc.test.config;

import com.github.houbb.ioc.annotation.Autowired;
import com.github.houbb.ioc.annotation.Configuration;
import com.github.houbb.ioc.annotation.Import;
import com.github.houbb.ioc.test.model.Book;
import com.github.houbb.ioc.test.service.WeightApple;

/**
 * <p> project: ioc-AppConfig </p>
 * <p> create on 2019/11/19 23:01 </p>
 *
 * @author Administrator
 * @since 0.1.7
 */
@Configuration
@Import(AppMultiBeanConfig.class)
public class AppAutowiredPrimaryConfig {

    @Autowired
    private Book book;

    /**
     * 返回书籍
     * @return 书籍
     * @since 0.1.7
     */
    public Book getBook() {
        return book;
    }

}
