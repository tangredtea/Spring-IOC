[![AUR](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/laowenruo/Spring-IOC/master/LICENSE)
[![stars](https://badgen.net/github/stars//laowenruo/Spring-IOC)](https://github.com//laowenruo/Spring-IOC/stargazers)
[![contributors](https://badgen.net/github/contributors/laowenruo/Spring-IOC)](https://github.com/laowenruo/Spring-IOC/graphs/contributors)
[![help-wanted](https://badgen.net/github/label-issues/laowenruo/Spring-IOC/help%20wanted/open)](https://github.com/laowenruo/Spring-IOC/labels/help%20wanted)
[![issues](https://badgen.net/github/open-issues/laowenruo/Spring-IOC)](https://github.com/laowenruo/Spring-IOC/issues)
[![PRs Welcome](https://badgen.net/badge/PRs/welcome/green)](http://makeapullrequest.com)

[English](README.md) | [简体中文](README_CN.md)

# 🌱 My-Spring-IOC

> 🚀 通过从零实现 Spring 框架，深入理解其核心原理！

一个轻量级、教育性质的 Spring IOC 容器实现，将 Spring 的魔法变为现实。本项目通过实现依赖注入、AOP 代理、事件驱动架构、类型转换和迷你 MVC 框架，用清晰易读的代码揭开 Spring 的核心机制。

## ✨ 为什么要做这个项目？

你是否曾好奇 Spring 的"魔法"是如何实现的？这个项目将为你揭秘：
- `@Autowired` 是如何知道要注入什么的？
- AOP 代理背后到底发生了什么？
- Spring 是如何解决循环依赖的？
- IOC 容器的运作机制是什么？

非常适合想要通过理解内部原理来提升 Spring 技能的开发者！

## 📚 文档

深入了解实现细节：
- [手写 Spring XML 注入](https://github.com/laowenruo/Spring-IOC/blob/master/docs/Spring-Handwritten-XML-Injection.md)
- [手写 Spring 注解注入](https://github.com/laowenruo/Spring-IOC/blob/master/docs/Spring-Handwritten-Annotation-Injection.md)
- [手写 Spring 简易 MVC](https://github.com/laowenruo/Spring-IOC/blob/master/docs/Spring-Handwritten-Simple-MVC.md)

## 🎯 已实现功能

- ✅ 基于 XML 的 Bean 注入
- ✅ 基于注解的 Bean 注入
- ✅ 基础 MVC 功能与路由映射
- ✅ AOP（面向切面编程）支持
- ✅ 循环依赖解决方案（三级缓存机制）
- ✅ 事件驱动架构
- ✅ 类型转换系统

## 🔧 注入支持

- 🔄 递归注入
- 📦 属性注入
- 🔗 引用注入（支持循环依赖解决）
- 🏷️ 基于注解的配置：
  - `@Component` - 将类标记为 Spring Bean
  - `@Autowired` - 自动依赖注入
  - `@Value` - 从配置文件注入值
  - `@Qualifier` - 指定要注入的 Bean
  - `@Scope` - 定义 Bean 作用域（单例/原型）
- 🎭 单例和原型作用域切换

## 🎨 使用的设计模式

本项目展示了实际应用中的设计模式：

- 🏭 **工厂模式**：通过 `BeanFactory` 和 `ApplicationContext` 创建 Bean
- 🎯 **单例模式**：Bean 默认为单例作用域
- 🔌 **适配器模式**：AOP 通知适配和 MVC 控制器适配
- 🎭 **代理模式**：使用 JDK 和 CGLIB 动态代理实现 AOP
- 📋 **模板方法模式**：抽象类定义骨架算法（`AbstractBeanFactory`、`AbstractApplicationContext`）
- 👀 **观察者模式**：通过 `ApplicationEvent` 和 `ApplicationListener` 实现事件机制

## 🚀 快速开始

```bash
# 克隆仓库
git clone https://github.com/laowenruo/Spring-IOC.git

# 进入项目目录
cd Spring-IOC

# 使用 Maven 构建
mvn clean install
```

## 🤝 贡献

贡献让开源社区成为一个令人惊叹的学习、启发和创造的地方。非常感谢你的任何贡献！

1. Fork 本项目
2. 创建你的特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交你的更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启一个 Pull Request

## 📝 许可证

本项目采用 Apache License 2.0 许可证。详见 `LICENSE` 文件。

## 💡 致谢

- 灵感来源于 Spring 框架
- 为学习和教育目的而构建
- 特别感谢所有贡献者！

---

⭐ 如果这个项目对你有帮助，请考虑给它一个 Star！
