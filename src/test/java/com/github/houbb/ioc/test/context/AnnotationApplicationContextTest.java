package com.github.houbb.ioc.test.context;

import com.github.houbb.ioc.context.AnnotationApplicationContext;
import com.github.houbb.ioc.context.JsonApplicationContext;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.test.config.AppConfig;
import com.github.houbb.ioc.test.service.Apple;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p> project: ioc-JsonApplicationContextTest </p>
 * <p> create on 2019/11/6 20:09 </p>
 *
 * @author Administrator
 * @since 0.1.1
 */
public class AnnotationApplicationContextTest {

    /**
     * 实现最基本的 Config 测试
     * @since 0.1.1
     */
    @Test
    public void simpleTest() {
        BeanFactory beanFactory = new AnnotationApplicationContext(AppConfig.class);
        AppConfig config = beanFactory.getBean("appConfig", AppConfig.class);

        Assert.assertEquals("app config", config.name());
    }

}
