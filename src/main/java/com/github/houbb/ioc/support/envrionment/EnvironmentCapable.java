package com.github.houbb.ioc.support.envrionment;

/**
 * <p> project: ioc-Environment </p>
 * <p> create on 2019/11/30 14:37 </p>
 *
 * @author Administrator
 * @since 0.1.9
 */
public interface EnvironmentCapable {

    /**
     * 获取环境信息依赖
     * @return Return the {@link Environment} associated with this component.
     */
    Environment getEnvironment();

}
