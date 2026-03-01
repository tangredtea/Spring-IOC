[![AUR](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/laowenruo/Spring-IOC/master/LICENSE)
[![stars](https://badgen.net/github/stars//laowenruo/Spring-IOC)](https://github.com//laowenruo/Spring-IOC/stargazers)
[![contributors](https://badgen.net/github/contributors/laowenruo/Spring-IOC)](https://github.com/laowenruo/Spring-IOC/graphs/contributors)
[![help-wanted](https://badgen.net/github/label-issues/laowenruo/Spring-IOC/help%20wanted/open)](https://github.com/laowenruo/Spring-IOC/labels/help%20wanted)
[![issues](https://badgen.net/github/open-issues/laowenruo/Spring-IOC)](https://github.com/laowenruo/Spring-IOC/issues)
[![PRs Welcome](https://badgen.net/badge/PRs/welcome/green)](http://makeapullrequest.com)
# My-Spring-IOC
A simplified implementation of the Spring IOC container, covering core features such as dependency injection, AOP proxy, event mechanism, type conversion, and a basic MVC framework with route mapping.

## Documentation
- [Handwritten Spring XML Injection](https://github.com/laowenruo/Spring-IOC/blob/master/docs/Spring-Handwritten-XML-Injection.md)
- [Handwritten Spring Annotation Injection](https://github.com/laowenruo/Spring-IOC/blob/master/docs/Spring-Handwritten-Annotation-Injection.md)
- [Handwritten Spring Simple MVC](https://github.com/laowenruo/Spring-IOC/blob/master/docs/Spring-Handwritten-Simple-MVC.md)

### Implemented Features
- XML-based bean injection
- Annotation-based bean injection
- Basic MVC functionality
- AOP (Aspect-Oriented Programming) support
- Circular dependency resolution (three-level cache)

### Injection Support
- Recursive injection
- Property injection
- Reference injection (with circular dependency resolution)
- Annotation-based configuration (`@Component`, `@Autowired`, `@Value`, `@Qualifier`, `@Scope`)
- Singleton and prototype scope switching

### Design Patterns Used
- **Factory Pattern**: Spring uses the Factory pattern to create bean objects via `BeanFactory` and `ApplicationContext`.
- **Singleton Pattern**: Beans in Spring are singletons by default.
- **Adapter Pattern**: Spring AOP advice uses the Adapter pattern; Spring MVC also uses the Adapter pattern for Controller adaptation.
- **Proxy Pattern**: AOP proxy creation using JDK dynamic proxy and CGLIB.
- **Template Method Pattern**: `AbstractBeanFactory` and `AbstractApplicationContext` define skeleton algorithms, deferring steps to subclasses.
- **Observer Pattern**: The event mechanism (`ApplicationEvent` / `ApplicationListener`) implements the Observer pattern.
