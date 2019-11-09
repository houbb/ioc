package com.github.houbb.ioc.test.service;

import com.github.houbb.ioc.annotation.FactoryMethod;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * <p> project: ioc-Apple </p>
 * <p> create on 2019/11/6 20:02 </p>
 *
 * @author Administrator
 * @since 0.0.6
 */
public class ColorApple {

    /**
     * 颜色
     * @since 0.0.6
     */
    private String color;

    /**
     * 自定义实例
     * @return 自定义结果
     * @since 0.0.6
     */
    @FactoryMethod
    public static ColorApple newInstance() {
        ColorApple colorApple = new ColorApple();
        colorApple.setColor("define");
        return colorApple;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "ColorApple{" +
                "color='" + color + '\'' +
                '}';
    }

}
