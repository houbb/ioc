package com.github.houbb.ioc.support.envrionment.impl;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.ioc.constant.ProfileConst;
import com.github.houbb.ioc.support.envrionment.ConfigurableEnvironment;
import com.github.houbb.ioc.support.envrionment.PropertySource;

import java.util.List;

/**
 * <p> project: ioc-Environment </p>
 * <p> create on 2019/11/30 14:37 </p>
 *
 * @author Administrator
 * @since 0.1.9
 * @see com.github.houbb.ioc.annotation.Profile 环境标识注解
 */
public class DefaultEnvironment implements ConfigurableEnvironment {

    /**
     * 被激活的信息
     * @since 0.1.9
     */
    private String[] activeProfiles = new String[]{ProfileConst.DEFAULT};

    /**
     * 资源列表
     * @since 0.1.10
     */
    private List<PropertySource> propertySources;

    /**
     * 默认实例
     * @return 默认实例
     * @since 0.1.9
     */
    public static ConfigurableEnvironment defaultInstance() {
        return new DefaultEnvironment();
    }

    @Override
    public void setActiveProfiles(String... activeProfiles) {
        ArgUtil.notEmpty(activeProfiles, "activeProfiles");

        this.activeProfiles = activeProfiles;
    }

    @Override
    public String[] getActiveProfiles() {
        return activeProfiles;
    }

}
