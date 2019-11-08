package com.github.houbb.ioc.context;

import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.ioc.core.impl.DefaultBeanFactory;
import com.github.houbb.ioc.core.impl.DefaultListableBeanFactory;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.ioc.model.impl.DefaultBeanDefinition;
import com.github.houbb.json.bs.JsonBs;

import java.io.InputStream;
import java.util.List;

/**
 * JSON 应用上下文
 * @author binbin.hou
 * @since 0.0.1
 */
public class JsonApplicationContext extends DefaultListableBeanFactory {

    /**
     * 文件名称
     * @since 0.0.1
     */
    private final String fileName;

    public JsonApplicationContext(String fileName) {
        this.fileName = fileName;

        // 初始化配置
        this.init();
    }

    /**
     * 初始化配置相关信息
     *
     * <pre>
     *  new TypeReference<List<BeanDefinition>>(){}
     * </pre>
     *
     * 读取文件：https://blog.csdn.net/feeltouch/article/details/83796764
     * @since 0.0.1
     */
    private void init() {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        final String jsonConfig = FileUtil.getFileContent(is);
        List<DefaultBeanDefinition> beanDefinitions = JsonBs.deserializeArray(jsonConfig, DefaultBeanDefinition.class);
        if(CollectionUtil.isNotEmpty(beanDefinitions)) {
            for (BeanDefinition beanDefinition : beanDefinitions) {
                super.registerBeanDefinition(beanDefinition.getName(), beanDefinition);
            }
        }
    }

}
