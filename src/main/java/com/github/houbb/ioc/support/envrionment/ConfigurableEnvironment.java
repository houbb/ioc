package com.github.houbb.ioc.support.envrionment;

/**
 * <p> project: ioc-Environment </p>
 * <p> create on 2019/11/30 14:37 </p>
 *
 * @author Administrator
 * @since 0.1.9
 * @see com.github.houbb.ioc.annotation.Profile 环境标识注解
 */
public interface ConfigurableEnvironment extends Environment {

    /**
     * 设置激活的环境信息
     * @param activeProfiles 激活的环境信息
     * @since 0.1.9
     */
    void setActiveProfiles(final String... activeProfiles);

}
