package com.github.houbb.ioc.test.config;

import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.ioc.annotation.Autowired;
import com.github.houbb.ioc.annotation.Configuration;
import com.github.houbb.ioc.annotation.Import;
import com.github.houbb.ioc.test.model.Book;

import java.util.List;

/**
 * <p> project: ioc-AppConfig </p>
 * <p> create on 2019/11/19 23:01 </p>
 *
 * @author Administrator
 * @since 0.1.6
 */
@Configuration
@Import(AppMultiBeanConfig.class)
public class AppAutowiredCollectionConfig {

    /**
     * 彩色书籍列表
     * @since 0.1.6
     */
    @Autowired
    private List<Book> bookList;

    /**
     * 获取苹果彩色列表
     * @return 列表
     * @since 0.1.6
     */
    public List<String> getAppleColorList() {
        List<String> bookNameList = Guavas.newArrayList();

        for(Book book : bookList) {
            bookNameList.add(book.getName());
        }
        return bookNameList;
    }

}
