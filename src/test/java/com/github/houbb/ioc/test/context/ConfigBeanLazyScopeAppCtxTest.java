package com.github.houbb.ioc.test.context;

import com.github.houbb.ioc.context.AnnotationApplicationContext;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.test.config.AppBeanConfig;
import com.github.houbb.ioc.test.config.AppBeanLazyScopeConfig;
import com.github.houbb.ioc.test.service.Apple;
import com.github.houbb.ioc.test.service.WeightApple;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p> project: ioc-JsonApplicationContextTest </p>
 * <p> create on 2019/11/6 20:09 </p>
 *
 * @author Administrator
 * @since 0.1.3
 */
public class ConfigBeanLazyScopeAppCtxTest {

    /**
     * 添加 scope lazy-init 测试
     * @since 0.1.3
     */
    @Test
    public void weightAppleTest() {
        BeanFactory beanFactory = new AnnotationApplicationContext(AppBeanLazyScopeConfig.class);
        WeightApple weightAppleOne = beanFactory.getBean("weightApple", WeightApple.class);
        WeightApple weightAppleTwo = beanFactory.getBean("weightApple", WeightApple.class);
        Assert.assertSame(weightAppleOne, weightAppleTwo);

        Apple appleOne = beanFactory.getBean("apple", Apple.class);
        Apple appleTwo = beanFactory.getBean("apple", Apple.class);
        Assert.assertNotSame(appleOne, appleTwo);
    }

}
