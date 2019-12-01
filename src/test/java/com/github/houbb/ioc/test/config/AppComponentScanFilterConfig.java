package com.github.houbb.ioc.test.config;

import com.github.houbb.ioc.annotation.ComponentScan;
import com.github.houbb.ioc.annotation.Configuration;
import com.github.houbb.ioc.annotation.Service;

/**
 * 排除 service 指定的属性
 * <p> project: ioc-AppConfig </p>
 * <p> create on 2019/11/19 23:01 </p>
 *
 * @author Administrator
 * @since 0.1.11
 */
@Configuration
@ComponentScan(value = "com.github.houbb.ioc.test.service.inner", excludes = Service.class)
public class AppComponentScanFilterConfig {
}
