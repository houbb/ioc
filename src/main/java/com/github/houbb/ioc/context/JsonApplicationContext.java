package com.github.houbb.ioc.context;

import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.ioc.model.BeanDefinition;
import com.github.houbb.json.bs.JsonBs;

import java.io.InputStream;
import java.util.List;

/**
 * JSON 应用上下文
 *
 * 所有的 applicationContext 应该有一个完整的
 *
 * 读取文件：https://blog.csdn.net/feeltouch/article/details/83796764
 * @author binbin.hou
 * @since 0.0.1
 */
public class JsonApplicationContext extends AbstractApplicationContext {

    /**
     * 文件名称
     * @since 0.0.1
     */
    private final String fileName;

    public JsonApplicationContext(String fileName) {
        this.fileName = fileName;

        super.init();
    }

    /**
     * 构建对象属性列表
     * @return 对象属性列表
     * @since 0.0.4
     */
    @Override
    protected List<BeanDefinition> buildBeanDefinitionList() {
        //1. 读取配置信息
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        final String jsonConfig = FileUtil.getFileContent(is);

        //2. 配置信息转化为标准的 beanDefinition
        return JsonBs.deserializeArray(jsonConfig, BeanDefinition.class);
    }

}
