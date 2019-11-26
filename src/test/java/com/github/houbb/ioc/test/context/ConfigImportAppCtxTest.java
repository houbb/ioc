package com.github.houbb.ioc.test.context;

import com.github.houbb.ioc.context.AnnotationApplicationContext;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.test.config.AppImportConfig;
import com.github.houbb.ioc.test.service.WeightApple;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p> project: ioc-JsonApplicationContextTest </p>
 * <p> create on 2019/11/6 20:09 </p>
 *
 * @author Administrator
 * @since 0.1.4
 */
public class ConfigImportAppCtxTest {

    /**
     * 添加 @Import 支持
     * @since 0.1.4
     */
    @Test
    public void importTest() {
        BeanFactory beanFactory = new AnnotationApplicationContext(AppImportConfig.class);
        WeightApple weightApple = beanFactory.getBean("weightApple", WeightApple.class);

        Assert.assertEquals("10", weightApple.getWeight());
    }

}
