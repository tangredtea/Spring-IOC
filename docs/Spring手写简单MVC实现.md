# Spring手写简单MVC实现

> 本篇文章为本人在参考其他人教程的时候手动实现SpringMVC的一点心得，由别人的教程以及自己的心得整合，如果发现有错误，还望告知，github链接：https://github.com/laowenruo/Spring-IOC  

## SpringMVC的大致原理

### 运行过程

![](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.west.cn%2Finfo%2Fupload%2F20190220%2Fbm0mjnivg0g.jpg&refer=http%3A%2F%2Fwww.west.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1619531808&t=3e7bd0425276dcf649114398e606881f)

- 用户发送请求至前端控制器 DispatcherServlet（DispatcherServlet为MVC中最核心的类了）。
- DispatcherServlet 收到请求调用 HandlerMapping 处理器映射器。
- 处理器映射器根据请求url找到具体的处理器，生成处理器对象及处理器拦截器(如果有则生成)一并返回给 DispatcherServlet。 
- DispatcherServlet 通过 HandlerAdapter 处理器适配器调用处理器。 
- 执行处理器（Controller，也叫后端控制器）。 
- Controller 执行完成返回 ModelAndView。 
- HandlerAdapter 将 controller 执行结果 ModelAndView 返回给 DispatcherServlet。 
- DispatcherServlet 将 ModelAndView 传给 ViewResolver 视图解析器。 
- ViewResolver 解析后返回具体 View。 
- DispatcherServlet 对 View 进行渲染视图（即将模型数据填充至视图中）。 
- DispatcherServlet 响应用户。

### 实现

+ 添加servlet依赖，一切web核心都靠servlet（jsp本质也是servlet）

  ```xml
  <dependencies>
      <dependency>
          <groupId>javax.servlet</groupId>
          <artifactId>javax.servlet-api</artifactId>
          <version>4.0.1</version>
          <scope>provided</scope>
      </dependency>
  </dependencies>
  ```

  

+ 添加web结构，并且在WEB-INF下添加web.xml（用来配置servlet）

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns="http://java.sun.com/xml/ns/javaee"
           xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
           version="3.0">
      <servlet>
          <servlet-name>MySpringMVC</servlet-name>
          <servlet-class>XXXX.DispatcherServlet</servlet-class>
          <init-param>
              <param-name>contextConfigLocation</param-name>
              <param-value>application.properties</param-value>
          </init-param>
          <load-on-startup>1</load-on-startup>
      </servlet>
      <servlet-mapping>
          <servlet-name>MySpringMVC</servlet-name>
          <url-pattern>/*</url-pattern>
      </servlet-mapping>
  
  </web-app>
  ```

  

+ web.xml解析：首先定义了DispatcherServlet的所在路径，定义得跟我们以前java web配置servlet一样，并且DispatcherServlet拦截全路径的（即/*），并且我们配置初始化文件，在代码中为application.properties，当然我们可以定义为其他文件，关于这个文件如下

  ```
  scanPackage=top.guoziyang.main.controller
  ```

### 注解实现

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {}           //controller注解实现处理器


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {        //RequestMapping实现请求映射器   
    String value() default "";
}

 
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {      //RequestParam实现请求参数处理
    String value();
}
```

#### 实现核心类-DispatcherServlet

> init 方法首先初始化了一个 Spring 容器。其主要的功能就是读取配置文件，接着扫描目标包下所有的 Controller，最后实例化所有的 Controller，并且绑定 URL 路由。

#### init方法

```
@Override
public void init(ServletConfig config) {
    try {
        xmlApplicationContext = new ClassPathXmlApplicationContext("application-annotation.xml");
    } catch (Exception e) {
        e.printStackTrace();
    }
    doLoadConfig(config.getInitParameter("contextConfigLocation"));
    doScanner(properties.getProperty("scanPackage"));
    doInstance();
    initHandlerMapping();
}
```

------

#### doInstance() 方法实现

> 将上一步扫描的类中遍历一下，因为上次扫描的类都保存在属性中了，有controller注解的对象通过反射实例化，但我们上一步不是初始化了spring容器吗？这个时候如何添加bean呢？。这里给 `XmlApplicationContext` 类添加了一个 `refreshBeanFactory()` 方法，手动刷新Bean的配置，如果遇到没有初始化的（刚添加进去的）就会初始化。

```java
private void doInstance() {
    if (classNames.isEmpty()) {
        return;
    }
    for (String className : classNames) {
        try {
            //把类搞出来,反射来实例化(只有加 @Controller 需要实例化)
            Class clazz = Class.forName(className);
            if (clazz.isAnnotationPresent(Controller.class)) {
                classes.add(clazz);
                BeanDefinition definition = new BeanDefinition();
                definition.setSingleton(true);
                definition.setBeanClassName(clazz.getName());
                xmlApplicationContext.addNewBeanDefinition(clazz.getName(), definition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    try {
        xmlApplicationContext.refreshBeanFactory();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

> refreshBeanFactory（）

```java
public void refreshBeanFactory() throws Exception {
    prepareBeanFactory((AbstractBeanFactory) beanFactory);
}
```

注意这里我们还把符合条件的类（Controller）放在了 classes 里，这是一个 HashSet，后续在绑定 URL 的时候要用。

> 在 `initHandlerMapping()` 方法中，我们将扫描对应的 Controller，找出某个 URL 应当由哪个类的哪个方法进行处理。如下：

```java
private void initHandlerMapping() {
    if (classes.isEmpty()) return;
    try {
        for (Class<?> clazz : classes) {
            String baseUrl = "";
            if (clazz.isAnnotationPresent(RequestMapping.class)) {
                baseUrl = clazz.getAnnotation(RequestMapping.class).value();
            }
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(RequestMapping.class)) continue;
                String url = method.getAnnotation(RequestMapping.class).value();
                url = (baseUrl + "/" + url).replaceAll("/+", "/");
                handlerMapping.put(url, method);
                controllerMap.put(url, xmlApplicationContext.getBean(clazz));
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

#### 定义请求方法

```java
    public void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (handlerMapping.isEmpty()) return;
        String url = request.getRequestURI();
        String contextPath = request.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        if (!handlerMapping.containsKey(url)) {
            response.getWriter().write("404 NOT FOUND!");
            return;
        }
        Method method = handlerMapping.get(url);
        Class<?>[] parameterTypes = method.getParameterTypes();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Object[] paramValues = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            String requestParam = parameterTypes[i].getSimpleName();
            if (requestParam.equals("HttpServletRequest")) {
                paramValues[i] = request;
                continue;
            }
            if (requestParam.equals("HttpServletResponse")) {
                paramValues[i] = response;
                continue;
            }
            if (requestParam.equals("String")) {
                for (Map.Entry<String, String[]> param : parameterMap.entrySet()) {
                    String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
                    paramValues[i] = value;
                }
            }
        }
        method.invoke(controllerMap.get(url), paramValues);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            //处理请求
            doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500!! Server Exception");
        }
    }
```

> 部分内容参考来自https://4m.cn/F26GP