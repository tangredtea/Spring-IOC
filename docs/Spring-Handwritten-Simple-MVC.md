# Handwritten Spring Simple MVC Implementation

> This article presents my insights from manually implementing Spring MVC while following other tutorials, combined with my own notes. If you find any errors, please let me know. GitHub link: https://github.com/laowenruo/Spring-IOC

## General Principles of Spring MVC

### Request Processing Flow

![](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.west.cn%2Finfo%2Fupload%2F20190220%2Fbm0mjnivg0g.jpg&refer=http%3A%2F%2Fwww.west.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1619531808&t=3e7bd0425276dcf649114398e606881f)

- The user sends a request to the front controller `DispatcherServlet` (the most core class in MVC).
- `DispatcherServlet` receives the request and invokes `HandlerMapping` (the handler mapper).
- The handler mapper finds the specific handler based on the request URL, generates the handler object and handler interceptors (if any), and returns them to `DispatcherServlet`.
- `DispatcherServlet` invokes the handler through `HandlerAdapter` (the handler adapter).
- The handler (`Controller`, also called the backend controller) is executed.
- The `Controller` returns a `ModelAndView` upon completion.
- `HandlerAdapter` returns the `Controller`'s `ModelAndView` result to `DispatcherServlet`.
- `DispatcherServlet` passes the `ModelAndView` to `ViewResolver` (the view resolver).
- `ViewResolver` resolves it and returns a concrete `View`.
- `DispatcherServlet` renders the view (populates model data into the view).
- `DispatcherServlet` sends the response to the user.

### Implementation

+ Add the servlet dependency — all web core functionality relies on servlets (JSP is essentially a servlet too).

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



+ Add the web structure, and add `web.xml` under `WEB-INF` (to configure the servlet):

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



+ `web.xml` explanation: First, we define the location of `DispatcherServlet`, configured just like in traditional Java web servlet configuration. `DispatcherServlet` intercepts all paths (`/*`). We also configure the initialization file — in the code it's `application.properties`, though you can name it differently. The content of this file is as follows:

  ```
  scanPackage=top.guoziyang.main.controller
  ```

### Annotation Implementation

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {}           // Controller annotation for handler

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {        // RequestMapping for request mapping
    String value() default "";
}


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {      // RequestParam for request parameter handling
    String value();
}
```

#### Implementing the Core Class — DispatcherServlet

> The `init` method first initializes a Spring container. Its main functionality is to read the configuration file, then scan all Controllers under the target package, and finally instantiate all Controllers and bind URL routes.

#### init Method

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

#### doInstance() Method Implementation

> This method iterates through the classes scanned in the previous step (which are stored in a property). Classes annotated with `@Controller` are instantiated via reflection. But since we already initialized a Spring container, how do we add beans? Here we add a `refreshBeanFactory()` method to the `XmlApplicationContext` class to manually refresh the bean configuration — any uninitialized beans (newly added) will be initialized.

```java
private void doInstance() {
    if (classNames.isEmpty()) {
        return;
    }
    for (String className : classNames) {
        try {
            // Load the class; instantiate via reflection (only @Controller classes need instantiation)
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

> refreshBeanFactory()

```java
public void refreshBeanFactory() throws Exception {
    prepareBeanFactory((AbstractBeanFactory) beanFactory);
}
```

Note that we also store qualifying classes (Controllers) in `classes`, which is a `HashSet`. This will be used later when binding URLs.

> In the `initHandlerMapping()` method, we scan the corresponding Controllers and find which URL should be handled by which class and method:

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

#### Defining Request Handling Methods

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
            // Handle the request
            doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500!! Server Exception");
        }
    }
```

> Partially referenced from https://4m.cn/F26GP
