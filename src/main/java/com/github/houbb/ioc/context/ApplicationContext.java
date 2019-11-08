package com.github.houbb.ioc.context;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.ioc.core.ListableBeanFactory;

/**
 * 应用上下文接口
 * <p> project: ioc-ApplicationContext </p>
 * <p> create on 2019/11/8 22:25 </p>
 *
 * @author Administrator
 * @since 0.0.4
 */
public interface ApplicationContext extends ListableBeanFactory {

    /**
     * 获取应用名称
     * @return 应用名称
     * @since 0.0.4
     */
    String getApplicationName();

}
