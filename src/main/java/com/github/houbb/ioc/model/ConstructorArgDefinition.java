package com.github.houbb.ioc.model;

/**
 * 构造器参数定义信息
 *
 * 这里看起来只是一些属性的处理，实际上必须要面对两个核心的问题：
 *
 * （1）String value 与指定参数类型之间的转换，可以抽象为接口。
 * 内置各种默认的实现方式
 *
 * （2）Ref 当一旦指定了这个值之后，就需要处理一个依赖关系。
 *  必须将这个依赖联调处理好。
 *
 * 【注解的参数下标】
 * （1）如果用户不指定，则按照指定的顺序从0开始递增
 * （2）所以这里使用对象，而不是 int 类型，就是为了避免反序列化等处理，无法区分开是否为用户指定信息。
 * （3）这个必须断言为不可重复。
 *
 * 个人感觉这样不合理，所以直接移除这个指定 index 的特性。
 * 同理 name 可能会比较方便，但是需要做一次映射处理，暂时不做支持。
 *
 * @author binbin.hou
 * @since 0.0.6
 */
public interface ConstructorArgDefinition {

    /**
     * 参数类型全称
     * @return 类型全称
     * @since 0.0.6
     */
    String getType();

    /**
     * 设置参数类型全称
     * @param type 参数类型全称
     * @since 0.0.6
     */
    void setType(final String type);

    /**
     * 值信息
     * @return 值信息
     * @since 0.0.6
     */
    String getValue();

    /**
     * 设置值
     * @param value 值
     * @since 0.0.6
     */
    void setValue(final String value);

    /**
     * 获取引用的名称
     * @return 引用的例子名称
     * @since 0.0.6
     */
    String getRef();

    /**
     * 设置引用属性名称
     * @param ref 引用属性名称
     * @since 0.0.6
     */
    void setRef(final String ref);

}
