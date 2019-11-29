package com.github.houbb.ioc.support.aware.service.impl;

import com.github.houbb.ioc.core.ListableBeanFactory;
import com.github.houbb.ioc.support.aware.BeanNameAware;
import com.github.houbb.ioc.support.aware.service.AwareService;

import java.util.List;

/**
 * 默认实现
 * @author binbin.hou
 * @since 0.1.8
 */
public class DefaultAwareService implements AwareService {

    /**
     * 对象工厂
     * @since 0.1.8
     */
    private ListableBeanFactory beanFactory;

    @Override
    public AwareService setBeanFactory(ListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        return this;
    }

    @Override
    public AwareService notifyAllAware(String beanName) {
        List<BeanNameAware> awareList = beanFactory.getBeans(BeanNameAware.class);

        for (BeanNameAware aware : awareList) {
            aware.setBeanName(beanName);
        }

        return this;
    }


}
