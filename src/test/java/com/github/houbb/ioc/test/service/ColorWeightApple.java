package com.github.houbb.ioc.test.service;

/**
 * <p> project: ioc-Apple </p>
 * <p> create on 2019/11/6 20:02 </p>
 *
 * @author Administrator
 * @since 0.0.6
 */
public class ColorWeightApple {

    /**
     * 重量
     * @since 0.0.6
     */
    private Integer weight;

    /**
     * 颜色
     * @since 0.0.6
     */
    private String color;

    public ColorWeightApple(final ColorApple colorApple, Integer weight) {
        this.color = colorApple.getColor();
        this.weight = weight;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "ColorWeightApple{" +
                "weight=" + weight +
                ", color='" + color + '\'' +
                '}';
    }

}
