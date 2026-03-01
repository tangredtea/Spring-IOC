[![AUR](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/laowenruo/Spring-IOC/master/LICENSE)
[![stars](https://badgen.net/github/stars//laowenruo/Spring-IOC)](https://github.com//laowenruo/Spring-IOC/stargazers)
[![contributors](https://badgen.net/github/contributors/laowenruo/Spring-IOC)](https://github.com/laowenruo/Spring-IOC/graphs/contributors)
[![help-wanted](https://badgen.net/github/label-issues/laowenruo/Spring-IOC/help%20wanted/open)](https://github.com/laowenruo/Spring-IOC/labels/help%20wanted)
[![issues](https://badgen.net/github/open-issues/laowenruo/Spring-IOC)](https://github.com/laowenruo/Spring-IOC/issues)
[![PRs Welcome](https://badgen.net/badge/PRs/welcome/green)](http://makeapullrequest.com)

[English](README.md) | [简体中文](README_CN.md)

# 🌱 My-Spring-IOC

> 🚀 A hands-on journey to understand Spring Framework by building it from scratch!

A lightweight, educational implementation of the Spring IOC container that brings the magic of Spring to life. This project demystifies Spring's core mechanisms by implementing dependency injection, AOP proxies, event-driven architecture, type conversion, and a mini MVC framework - all with clean, readable code.

## ✨ Why This Project?

Ever wondered how Spring's "magic" actually works? This project peels back the curtain on:
- How does `@Autowired` know what to inject?
- What's really happening with AOP proxies?
- How does Spring solve circular dependencies?
- What makes the IOC container tick?

Perfect for developers who want to level up their Spring knowledge by understanding the internals!

## 📚 Documentation

Dive deep into the implementation details:
- [Handwritten Spring XML Injection](https://github.com/laowenruo/Spring-IOC/blob/master/docs/Spring-Handwritten-XML-Injection.md)
- [Handwritten Spring Annotation Injection](https://github.com/laowenruo/Spring-IOC/blob/master/docs/Spring-Handwritten-Annotation-Injection.md)
- [Handwritten Spring Simple MVC](https://github.com/laowenruo/Spring-IOC/blob/master/docs/Spring-Handwritten-Simple-MVC.md)

## 🎯 Implemented Features
## 🎯 Implemented Features

- ✅ XML-based bean injection
- ✅ Annotation-based bean injection
- ✅ Basic MVC functionality with route mapping
- ✅ AOP (Aspect-Oriented Programming) support
- ✅ Circular dependency resolution (three-level cache mechanism)
- ✅ Event-driven architecture
- ✅ Type conversion system

## 🔧 Injection Support

- 🔄 Recursive injection
- 📦 Property injection
- 🔗 Reference injection with circular dependency resolution
- 🏷️ Annotation-based configuration:
  - `@Component` - Mark classes as Spring beans
  - `@Autowired` - Automatic dependency injection
  - `@Value` - Inject values from properties
  - `@Qualifier` - Specify which bean to inject
  - `@Scope` - Define bean scope (singleton/prototype)
- 🎭 Singleton and prototype scope switching

## 🎨 Design Patterns Used
## 🎨 Design Patterns Used

This project showcases real-world design patterns in action:

- 🏭 **Factory Pattern**: Bean creation through `BeanFactory` and `ApplicationContext`
- 🎯 **Singleton Pattern**: Default singleton scope for beans
- 🔌 **Adapter Pattern**: AOP advice adaptation and MVC controller adaptation
- 🎭 **Proxy Pattern**: Dynamic proxies using JDK and CGLIB for AOP
- 📋 **Template Method Pattern**: Abstract classes define skeleton algorithms (`AbstractBeanFactory`, `AbstractApplicationContext`)
- 👀 **Observer Pattern**: Event mechanism with `ApplicationEvent` and `ApplicationListener`

## 🚀 Getting Started

```bash
# Clone the repository
git clone https://github.com/laowenruo/Spring-IOC.git

# Navigate to the project
cd Spring-IOC

# Build with Maven
mvn clean install
```

## 🤝 Contributing

Contributions are what make the open-source community amazing! Any contributions you make are greatly appreciated.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

Distributed under the Apache License 2.0. See `LICENSE` for more information.

## 💡 Acknowledgments

- Inspired by the Spring Framework
- Built for learning and educational purposes
- Special thanks to all contributors!

---

⭐ If you find this project helpful, please consider giving it a star!
