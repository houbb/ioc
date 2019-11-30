package com.github.houbb.ioc.support.envrionment.impl;

import java.util.Map;
import java.util.Properties;

/**
 * <p> project: ioc-MapPropertySource </p>
 * <p> create on 2019/11/30 21:55 </p>
 *
 * @author Administrator
 * @since 0.1.10
 */
public class PropertiesPropertySource extends MapPropertySource {

    @SuppressWarnings("unchecked")
    public PropertiesPropertySource(String name, Properties source) {
        super(name, (Map)  source);
    }

}
