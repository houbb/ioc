# IoC

Ioc 是一款 spring ioc 核心功能简化实现版本，便于学习和理解原理。

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.houbb/ioc/badge.svg)](http://mvnrepository.com/artifact/com.github.houbb/ioc)
[![Coverage Status](https://coveralls.io/repos/github/houbb/ioc/badge.svg?branch=master)](https://coveralls.io/github/houbb/ioc?branch=master)

## 定义

控制反转（Inversion of Control，缩写为IoC），是面向对象编程中的一种设计原则，可以用来减低计算机代码之间的耦合度。

其中最常见的方式叫做依赖注入（Dependency Injection，简称DI）。

通过控制反转，对象在被创建的时候，由一个调控系统内所有对象的外界实体，将其所依赖的对象的引用传递给它。

也可以说，依赖被注入到对象中。

## 创作目的

使用 spring 很长时间，对于 spring 使用非常频繁，实际上对于源码一直没有静下心来学习过。

但是 spring 源码存在一个问题，那就是过于抽象，导致学习起来成本上升。

所以本项目由渐入深，只实现 spring 的核心功能，便于自己和他人学习 spring 的核心原理。

# 快速开始

## maven 引入

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>ioc</artifactId>
    <version>最新版本</version>
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