package com.github.houbb.ioc.test.context;

import com.github.houbb.ioc.context.AnnotationApplicationContext;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.test.config.AppBeanConfig;
import com.github.houbb.ioc.test.config.AppConfig;
import com.github.houbb.ioc.test.service.Apple;
import com.github.houbb.ioc.test.service.WeightApple;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p> project: ioc-JsonApplicationContextTest </p>
 * <p> create on 2019/11/6 20:09 </p>
 *
 * @author Administrator
 * @since 0.1.2
 */
public class ConfigBeanApplicationContextTest {

    /**
     * 实现最基本的 Config-bean 测试
     * @since 0.1.2
     */
    @Test
    public void weightAppleTest() {
        BeanFactory beanFactory = new AnnotationApplicationContext(AppBeanConfig.class);
        WeightApple apple = beanFactory.getBean("weightApple", WeightApple.class);

        Assert.assertEquals("10", apple.getWeight());
    }

}
