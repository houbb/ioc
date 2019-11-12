package com.github.houbb.ioc.support.order;

/**
 * bean 的管理中经常会用到各种 order 的管理。
 * <p> project: ioc-Order </p>
 * <p> create on 2019/11/12 23:14 </p>
 *
 * @author Administrator
 * @since 0.0.8
 */
public interface Order {

    /**
     * 获取指定的顺序
     * @return 顺序编号
     * @since 0.0.8
     */
    int getOrder();

}
