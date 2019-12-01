package com.github.houbb.ioc.test.context;

import com.github.houbb.ioc.constant.ProfileConst;
import com.github.houbb.ioc.context.AnnotationApplicationContext;
import com.github.houbb.ioc.context.JsonApplicationContext;
import com.github.houbb.ioc.core.BeanFactory;
import com.github.houbb.ioc.support.envrionment.ConfigurableEnvironment;
import com.github.houbb.ioc.support.envrionment.Environment;
import com.github.houbb.ioc.support.envrionment.impl.DefaultEnvironment;
import com.github.houbb.ioc.test.config.*;
import com.github.houbb.ioc.test.model.Book;
import com.github.houbb.ioc.test.service.Apple;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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

    /**
     * 自动装配测试
     * @since 0.1.6
     */
    @Test
    public void autowiredTest() {
        BeanFactory beanFactory = new AnnotationApplicationContext(AppAutowiredConfig.class);
        AppAutowiredConfig appAutowiredConfig = beanFactory.getBean("appAutowiredConfig", AppAutowiredConfig.class);
        Assert.assertEquals("10", appAutowiredConfig.getWeight());
    }

    /**
     * 自动装配测试
     * @since 0.1.6
     */
    @Test
    public void autowiredListTest() {
        BeanFactory beanFactory = new AnnotationApplicationContext(AppAutowiredCollectionConfig.class);
        AppAutowiredCollectionConfig appAutowiredCollectionConfig = beanFactory.getBean("appAutowiredCollectionConfig", AppAutowiredCollectionConfig.class);

        List<String> bookNames = appAutowiredCollectionConfig.getAppleColorList();
        Assert.assertTrue(bookNames.contains("good"));
        Assert.assertTrue(bookNames.contains("bad"));
    }

    /**
     * 优先级测试
     * @since 0.1.7
     */
    @Test
    public void autowiredPrimaryTest() {
        BeanFactory beanFactory = new AnnotationApplicationContext(AppAutowiredPrimaryConfig.class);
        AppAutowiredPrimaryConfig appAutowiredPrimaryConfig = beanFactory.getBean("appAutowiredPrimaryConfig", AppAutowiredPrimaryConfig.class);

        Book book = appAutowiredPrimaryConfig.getBook();
        Assert.assertEquals("good", book.getName());
    }

    /**
     * 条件测试
     * @since 0.1.8
     */
    @Test
    public void conditionalTest() {
        BeanFactory beanFactory = new AnnotationApplicationContext(AppBeanConditionConfig.class);

        Assert.assertFalse(beanFactory.containsBean("book"));
        Assert.assertTrue(beanFactory.containsBean("book2"));
    }

    /**
     * 环境测试
     * @since 0.1.9
     */
    @Test
    public void profileTest() {
        ConfigurableEnvironment devEnv = new DefaultEnvironment();
        devEnv.setActiveProfiles(ProfileConst.DEV);
        BeanFactory devBeanFactory = new AnnotationApplicationContext(devEnv, AppBeanProfileConfig.class);
        Assert.assertTrue(devBeanFactory.containsBean("devBook"));
        Assert.assertFalse(devBeanFactory.containsBean("testBook"));

        ConfigurableEnvironment testEnv = new DefaultEnvironment();
        testEnv.setActiveProfiles(ProfileConst.TEST);
        BeanFactory testBeanFactory = new AnnotationApplicationContext(testEnv, AppBeanProfileConfig.class);
        Assert.assertFalse(testBeanFactory.containsBean("devBook"));
        Assert.assertTrue(testBeanFactory.containsBean("testBook"));
    }

    /**
     * 自动装配环境测试
     * @since 0.1.10
     */
    @Test
    public void autowiredEnvTest() {
        ConfigurableEnvironment devEnv = new DefaultEnvironment();
        devEnv.setActiveProfiles(ProfileConst.DEV);

        BeanFactory devBeanFactory = new AnnotationApplicationContext(devEnv, AppAutowiredEnvConfig.class);
        AppAutowiredEnvConfig appAutowiredEnvConfig = devBeanFactory.getBean("appAutowiredEnvConfig",
                AppAutowiredEnvConfig.class);
        Assert.assertEquals("[dev]", Arrays.toString(appAutowiredEnvConfig.getProfiles()));
    }

    /**
     * {@link com.github.houbb.ioc.annotation.Value} 测试
     * @since 0.1.10
     */
    @Test
    public void propertyValueTest() {
        BeanFactory devBeanFactory = new AnnotationApplicationContext(AppPropertyValueConfig.class);
        AppPropertyValueConfig appPropertyValueConfig = devBeanFactory.getBean("appPropertyValueConfig",
                AppPropertyValueConfig.class);

        Assert.assertEquals("ryo", appPropertyValueConfig.getName());
    }

    /**
     * {@link com.github.houbb.ioc.annotation.Value} 测试
     * {@link com.github.houbb.ioc.annotation.PropertiesResource} 资源信息
     *
     * @since 0.1.10
     */
    @Test
    public void propertyResourceValueTest() {
        BeanFactory devBeanFactory = new AnnotationApplicationContext(AppPropertyResourceValueConfig.class);
        AppPropertyResourceValueConfig appPropertyResourceValueConfig = devBeanFactory
                .getBean("appPropertyResourceValueConfig", AppPropertyResourceValueConfig.class);

        Assert.assertEquals("hello", appPropertyResourceValueConfig.getName());
    }

}
