[![license](https://badgen.net/github/license/laowenruo/Spring-IOC?color=green)](https://github.com/laowenruo/Spring-IOC/master/LICENSE)
[![stars](https://badgen.net/github/stars//laowenruo/Spring-IOC)](https://github.com//laowenruo/Spring-IOC/stargazers)
[![contributors](https://badgen.net/github/contributors/laowenruo/Spring-IOC)](https://github.com/laowenruo/Spring-IOC/graphs/contributors)
[![help-wanted](https://badgen.net/github/label-issues/laowenruo/Spring-IOC/help%20wanted/open)](https://github.com/laowenruo/Spring-IOC/labels/help%20wanted)
[![issues](https://badgen.net/github/open-issues/laowenruo/Spring-IOC)](https://github.com/laowenruo/Spring-IOC/issues)
[![PRs Welcome](https://badgen.net/badge/PRs/welcome/green)](http://makeapullrequest.com)
# My-Spring-IOC
Spring IOC容器简单实现，实现了一些基本的核心功能以及简单的MVC基本框架及映射访问

## 说明文档
- [Spring手写XML注入方式](https://github.com/laowenruo/Spring-IOC/blob/master/docs/Spring%E6%89%8B%E5%86%99XML%E6%B3%A8%E5%85%A5%E6%96%B9%E5%BC%8F.md)
- [Spring手写注解注入方式](https://github.com/laowenruo/Spring-IOC/blob/master/docs/Spring%E6%89%8B%E5%86%99%E6%B3%A8%E8%A7%A3%E6%B3%A8%E5%85%A5%E6%96%B9%E5%BC%8F.md)
- [Spring手写简单MVC实现](https://github.com/laowenruo/Spring-IOC/blob/master/docs/Spring%E6%89%8B%E5%86%99%E7%AE%80%E5%8D%95MVC%E5%AE%9E%E7%8E%B0.md)
### 目前已实现：
- xml注入
- 注解注入
- 简单MVC功能
### 注入支持
- 递归注入
- 属性注入
- 引用注入（但未解决循环依赖）
- 注解配置
- 单例以及原型模式切换
### 运用的设计模式
- 工厂设计模式 : Spring使用工厂模式通过 BeanFactory、ApplicationContext 创建 bean 对象。
- 单例设计模式 : Spring 中的 Bean 默认都是单例的。
- 适配器模式 :Spring AOP 的增强或通知(Advice)使用到了适配器模式、spring MVC 中也是用到了适配器模式适配Controller。（体现了一点）

### TODO：
- AOP实现
- 循环依赖
