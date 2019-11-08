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
    <version>0.0.2</version>
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

# 源码分析

[01-BeanFactory 基本实现]()

[02-ListBeanFactory 基本实现]()

# 拓展阅读

[Java IOC-00-ioc 是什么](https://houbb.github.io/2019/11/06/java-ioc-01-overview)