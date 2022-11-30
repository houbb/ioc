# IoC

[Ioc](https://github.com/houbb/ioc) 是一款 spring ioc 核心功能简化实现版本，便于学习和理解原理。

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.houbb/ioc/badge.svg)](http://mvnrepository.com/artifact/com.github.houbb/ioc)

## 创作目的

使用 spring 很长时间，对于 spring 使用非常频繁，实际上对于源码一直没有静下心来学习过。

但是 spring 源码存在一个问题，那就是过于抽象，导致学习起来成本上升。

所以本项目由渐入深，只实现 spring 的核心功能，便于自己和他人学习 spring 的核心原理。

## spring 的核心 

Spring 的核心就是 spring-beans，后面的一切 spring-boot，spring-cloud 都是建立在这个地基之上。

当别人问你 spring 的时候，希望你可以谈谈自己对于 spring ioc 自己更深层的见解，而不是网上人云亦云的几句话。

# 什么是 IOC

控制反转（Inversion of Control，缩写为IoC），是面向对象编程中的一种设计原则，可以用来减低计算机代码之间的耦合度。

其中最常见的方式叫做依赖注入（Dependency Injection，简称DI）。

通过控制反转，对象在被创建的时候，由一个调控系统内所有对象的外界实体，将其所依赖的对象的引用传递给它。

也可以说，依赖被注入到对象中。

## 为什么需要 IOC

IoC 是解耦的一种方法。

我们知道Java 是一门面向对象的语言，在 Java 中 Everything is Object，我们的程序就是由若干对象组成的。

当我们的项目越来越大，合作的开发者越来越多的时候，我们的类就会越来越多，类与类之间的引用就会成指数级的增长。

![mess](https://segmentfault.com/img/remote/1460000013000748?w=370&h=240)

这样的工程简直就是灾难，如果我们引入 Ioc 框架。

由框架来维护类的生命周期和类之间的引用。

我们的系统就会变成这样：

![manager](https://segmentfault.com/img/remote/1460000013000749?w=370&h=247)

这个时候我们发现，我们类之间的关系都由 IoC 框架负责维护类，同时将类注入到需要的类中。

也就是类的使用者只负责使用，而不负责维护。

把专业的事情交给专业的框架来完成，大大的减少开发的复杂度。

# 快速开始

## maven 引入

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>ioc</artifactId>
    <version>0.1.11</version>
</dependency>
```

## 测试准备

全部测试代码，见 test 模块。

- Apple.java

```java
public class Apple {

    public void color() {
        System.out.println("Apple color: red. ");
    }

}
```

- apple.json

类似于 xml 的配置，我们暂时使用 json 进行配置验证。

```json
[
{"name":"apple","className":"com.github.houbb.ioc.test.service.Apple"}
]
```

## 执行测试

- 测试

```java
BeanFactory beanFactory = new JsonApplicationContext("apple.json");
Apple apple = (Apple) beanFactory.getBean("apple");
apple.color();
```

- 日志

```
Apple color: red.
```

# spring 基本实现流程

## 说明

spring-beans 一切都是围绕 bean 展开的。

BeanFactory 负责对 bean 进行生命周期的相关管理，本节展示第一小节的简单实现流程。

## spring 核心流程

Spring IoC 主要是以下几个步骤。

1. 初始化 IoC 容器。

2. 读取配置文件。

3. 将配置文件转换为容器识别对的数据结构（这个数据结构在Spring中叫做 BeanDefinition）

4. 利用数据结构依次实例化相应的对象

5. 注入对象之间的依赖关系

## BeanDefinition 的抽象

BeanDefinition 是 spring 对 java bean 属性的一个抽象，经过这一层抽象，配置文件可以是 xml/json/properties/yaml 等任意一种，
甚至包括注解扫包。

为 spring 的拓展带来极大的灵活性。

本框架考虑到实现的简单性，初步只实现了 json 和基于注解扫包两种方式。

后期如果有时间可以考虑添加 xml 的实现，其实更多是 xml 的解析工作量，核心流程已经全部实现。

# 实现源码节选

## BeanDefinition 相关

包含了对于 java bean 的基本信息抽象。

- BeanDefinition.java

其默认实现为 `DefaultBeanDefinition.java`，就是对接口实现的最基本的 java POJO

> 参见 [DefaultBeanDefinition](https://github.com/houbb/ioc/blob/release_0.0.1/src/main/java/com/github/houbb/ioc/model/impl/DefaultBeanDefinition.java)

```java
/**
 * 对象定义属性
 * @author binbin.hou
 * @since 0.0.1
 */
public interface BeanDefinition {

    /**
     * 名称
     * @return 名称
     * @since 0.0.1
     */
    String getName();

    /**
     * 设置名称
     * @param name 名称
     * @since 0.0.1
     */
    void setName(final String name);

    /**
     * 类名称
     * @return 类名称
     */
    String getClassName();

    /**
     * 设置类名称
     * @param className 类名称
     * @since 0.0.1
     */
    void setClassName(final String className);

}
```

## BeanFactory 核心管理相关

- BeanFactory.java

```java
/**
 * bean 工厂接口
 * @author binbin.hou
 * @since 0.0.1
 */
public interface BeanFactory {

    /**
     * 根据名称获取对应的实例信息
     * @param beanName bean 名称
     * @return 对象信息
     * @since 0.0.1
     */
    Object getBean(final String beanName);

    /**
     * 获取指定类型的实现
     * @param beanName 属性名称
     * @param tClass 类型
     * @param <T> 泛型
     * @return 结果
     * @since 0.0.1
     */
    <T> T getBean(final String beanName, final Class<T> tClass);

}
```

- DefaultBeanFactory.java

为接口最基础的实现，源码如下：

```java
/**
 * bean 工厂接口
 * @author binbin.hou
 * @since 0.0.1
 */
public class DefaultBeanFactory implements BeanFactory {

    /**
     * 对象信息 map
     * @since 0.0.1
     */
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * 对象 map
     * @since 0.0.1
     */
    private Map<String, Object> beanMap = new ConcurrentHashMap<>();

    /**
     * 注册对象定义信息
     * @since 0.0.1
     */
    protected void registerBeanDefinition(final String beanName, final BeanDefinition beanDefinition) {
        // 这里可以添加监听器
        this.beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public Object getBean(String beanName) {
        Object bean = beanMap.get(beanName);
        if(ObjectUtil.isNotNull(bean)) {
            // 这里直接返回的是单例，如果用户指定为多例，则每次都需要新建。
            return bean;
        }

        // 获取对应配置信息
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(ObjectUtil.isNull(beanDefinition)) {
            throw new IocRuntimeException(beanName + " not exists in bean define.");
        }

        // 直接根据
        Object newBean = createBean(beanDefinition);
        // 这里可以添加对应的监听器
        beanMap.put(beanName, newBean);
        return newBean;
    }

    /**
     * 根据对象定义信息创建对象
     * @param beanDefinition 对象定义信息
     * @return 创建的对象信息
     * @since 0.0.1
     */
    private Object createBean(final BeanDefinition beanDefinition) {
        String className = beanDefinition.getClassName();
        Class clazz = ClassUtils.getClass(className);
        return ClassUtils.newInstance(clazz);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanName, Class<T> tClass) {
        Object object = getBean(beanName);
        return (T)object;
    }

}
```

其中 ClassUtils 是基于 class 的反射工具类，详情见 [ClassUtils.java](https://github.com/houbb/ioc/blob/release_0.0.1/src/main/java/com/github/houbb/ioc/util/ClassUtils.java)

## JsonApplicationContext 

基于 json 配置文件实现的基本实现，使用方式见开始种的例子代码。

- JsonApplicationContext.java

```java
/**
 * JSON 应用上下文
 * @author binbin.hou
 * @since 0.0.1
 */
public class JsonApplicationContext extends DefaultBeanFactory {

    /**
     * 文件名称
     * @since 0.0.1
     */
    private final String fileName;

    public JsonApplicationContext(String fileName) {
        this.fileName = fileName;

        // 初始化配置
        this.init();
    }

    /**
     * 初始化配置相关信息
     *
     * <pre>
     *  new TypeReference<List<BeanDefinition>>(){}
     * </pre>
     *
     * 读取文件：https://blog.csdn.net/feeltouch/article/details/83796764
     * @since 0.0.1
     */
    private void init() {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        final String jsonConfig = FileUtil.getFileContent(is);
        List<DefaultBeanDefinition> beanDefinitions = JsonBs.deserializeArray(jsonConfig, DefaultBeanDefinition.class);
        if(CollectionUtil.isNotEmpty(beanDefinitions)) {
            for (BeanDefinition beanDefinition : beanDefinitions) {
                super.registerBeanDefinition(beanDefinition.getName(), beanDefinition);
            }
        }
    }

}
``` 

## 小结

至此，一个最基本的 spring ioc 就基本实现了。

如果你想继续学习，可以分别参考以下代码分支。

# 分支说明

[v0.0.1-BeanFactory 基本实现](https://github.com/houbb/ioc/tree/release_0.0.1)

[v0.0.2-ListBeanFactory 基本实现](https://github.com/houbb/ioc/tree/release_0.0.2)

[v0.0.3-单例和延迟加载](https://github.com/houbb/ioc/tree/release_0.0.3)

[v0.0.4-初始化和销毁方法](https://github.com/houbb/ioc/tree/release_0.0.4)

[v0.0.5-RespCode 添加和代码优化](https://github.com/houbb/ioc/tree/release_0.0.5)

[v0.0.6-构造器和 factoryMethod 新建对象](https://github.com/houbb/ioc/tree/release_0.0.6)

[v0.0.7-property 属性设置](https://github.com/houbb/ioc/tree/release_0.0.7)

[v0.0.8-Aware 监听器及 PostProcessor](https://github.com/houbb/ioc/tree/release_0.0.8)

[v0.0.9-Parent 属性继承](https://github.com/houbb/ioc/tree/release_0.0.9)

[v0.1.0-循环依赖检测](https://github.com/houbb/ioc/tree/release_0.1.0)

[v0.1.1-@Configuration-java 代码配置](https://github.com/houbb/ioc/tree/release_0.1.1)

[v0.1.2-@Bean-java 对象定义](https://github.com/houbb/ioc/tree/release_0.1.2)

[v0.1.3-@Lazy-@Scope-java 对象属性配置](https://github.com/houbb/ioc/tree/release_0.1.3)

[v0.1.4-@Import 配置导入](https://github.com/houbb/ioc/tree/release_0.1.4)

[v0.1.5-@Bean 参数构造以及 @Description](https://github.com/houbb/ioc/tree/release_0.1.5)

[v0.1.6-@Autowired 自动装配注解支持](https://github.com/houbb/ioc/tree/release_0.1.6)

[v0.1.7-@Primary 指定优先级注解](https://github.com/houbb/ioc/tree/release_0.1.7)

[v0.1.8-@Conditional 条件注解支持](https://github.com/houbb/ioc/tree/release_0.1.8)

[v0.1.9-Environment 和 @Profile 实现](https://github.com/houbb/ioc/tree/release_0.1.9)

[v0.1.10-Property 配置文件相关和 @Value/@PropertyResource 实现](https://github.com/houbb/ioc/tree/release_0.1.10)

[v0.1.11-@ComponentScan 文件包扫描支持](https://github.com/houbb/ioc/tree/release_0.1.11)

# 拓展阅读

[Java IOC-00-ioc 是什么](https://houbb.github.io/2019/11/06/java-ioc-01-overview)


# 中间件等工具开源矩阵

[heaven: 收集开发中常用的工具类](https://github.com/houbb/heaven)

[rpc: 基于 netty4 实现的远程调用工具](https://github.com/houbb/rpc)

[mq: 简易版 mq 实现](https://github.com/houbb/mq)

[ioc: 模拟简易版 spring ioc](https://github.com/houbb/ioc)

[mybatis: 简易版 mybatis](https://github.com/houbb/mybatis)

[cache: 渐进式 redis 缓存](https://github.com/houbb/cache)

[jdbc-pool: 数据库连接池实现](https://github.com/houbb/jdbc-pool)

[sandglass: 任务调度时间工具框架](https://github.com/houbb/sandglass)

[sisyphus: 支持注解的重试框架](https://github.com/houbb/sisyphus)

[resubmit: 防止重复提交框架，支持注解](https://github.com/houbb/resubmit)

[auto-log: 日志自动输出](https://github.com/houbb/auto-log)

[async: 多线程异步并行框架](https://github.com/houbb/async)

