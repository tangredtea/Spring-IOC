# Handwritten Spring Annotation Injection Notes

> This article may not be a tutorial on implementing Spring with annotations. It is simply a collection of insights I gathered while hand-writing Spring annotation-based injection. If you find any errors, please let me know. GitHub link: https://github.com/laowenruo/Spring-IOC

## Annotation-Based Implementation

### Understanding Meta-Annotations

- `@Retention`: Used on annotation definitions to indicate the annotation's lifecycle. Commonly `RetentionPolicy.RUNTIME` (available at runtime).
- `@Target`: Used on annotation definitions to specify where the custom annotation can be applied. Commonly `ElementType.TYPE` (class level).

### Defining Annotations

+ `@Component`: Applied to classes, indicating the class is a bean managed by the IOC container.
+ `@Scope`: Applied to classes, defaults to singleton; can be set to prototype.
+ `@Autowired`: Applied to fields, indicating that the container should automatically inject a bean of the matching type.
+ `@Qualifier`: Applied to fields, takes a string parameter to inject a bean with a specific name. Often used together with `@Autowired`.
+ `@Value`: Applied to fields, used to inject a value (primitive types).

### Annotation Implementation

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
    String name() default "";
}


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Scope {
    String value() default "singleton"; // Defaults to singleton
}


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Autowired{}


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Qualifier {
    String value();
}


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Value {
    String value();
}
```

------

Since this is not auto-configured, we need to enable component scanning in the configuration file:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans>
  <component-scan base-package="XXX.XXX"/>
</beans>
```



#### Scanning Annotations

> Previously, our `XmlBeanDefinitionReader` only parsed XML attributes via DOM to populate the factory. Now with annotation-based injection, we need to modify the method: if annotation attributes are found during package scanning, annotation processing takes priority. The scanning is recursive.

```java
protected void parseBeanDefinitions(Element root) {
  ...
  for(int i = 0; i < nodeList.getLength(); i ++) {
      if(nodeList.item(i) instanceof Element) {
          Element ele = (Element)nodeList.item(i);
          if(ele.getTagName().equals("component-scan")) {
              basePackage = ele.getAttribute("base-package");
              break;
          }
      }
  }
  if(basePackage != null) {
      parseAnnotation(basePackage);   // Recursive scanning method
      return;
  }
  ...
}
```

------

The `parseAnnotation()` method retrieves all classes under the target package and iterates through them:

```java
protected void parseAnnotation(String basePackage) {
    Set<Class<?>> classes = getClasses(basePackage);   // Get all classes in the package
    for(Class clazz : classes) {
        processAnnotationBeanDefinition(clazz);
    }
}
```

> When learning reflection, we know that `getAnnotation()` is used to retrieve annotations. This is a key method in annotation-based injection — it retrieves annotations to process corresponding operations. The injection behavior depends on the annotation values.

```java
protected void processAnnotationBeanDefinition(Class<?> clazz) {
    if(clazz.isAnnotationPresent(Component.class)) {
        String name = clazz.getAnnotation(Component.class).name();
        if(name == null || name.length() == 0) {
            name = clazz.getName();
        }
        String className = clazz.getName();
        boolean singleton = true;
        if(clazz.isAnnotationPresent(Scope.class) && "prototype".equals(clazz.getAnnotation(Scope.class).value())) {
            singleton = false;
        }
        BeanDefinition beanDefinition = new BeanDefinition();
        processAnnotationProperty(clazz, beanDefinition);
        beanDefinition.setBeanClassName(className);
        beanDefinition.setSingleton(singleton);
        getRegistry().put(name, beanDefinition);
    }
}
```

`processAnnotationProperty()` examines each field of the class to determine whether injection is needed:

```java
protected void processAnnotationProperty(Class<?> clazz, BeanDefinition beanDefinition) {

    Field[] fields = clazz.getDeclaredFields();
    for(Field field : fields) {
        String name = field.getName();
        if(field.isAnnotationPresent(Value.class)) {
            Value valueAnnotation = field.getAnnotation(Value.class);
            String value = valueAnnotation.value();
            if(value != null && value.length() > 0) {
                // Value injection takes priority
                beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
            }
        } else if(field.isAnnotationPresent(Autowired.class)) {
            if(field.isAnnotationPresent(Qualifier.class)) {
                Qualifier qualifier = field.getAnnotation(Qualifier.class);
                String ref = qualifier.value();
                if(ref == null || ref.length() == 0) {
                    throw new IllegalArgumentException("the value of Qualifier should not be null!");
                }
                BeanReference beanReference = new BeanReference(ref);
                beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
            } else {
                String ref = field.getType().getName();
                BeanReference beanReference = new BeanReference(ref);
                beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
            }
        }
    }
}
```



#### Reviewing the Overall Flow

> The flow is quite similar to the XML parsing flow from the previous article, except that XML reading is replaced with annotation reading.

- Define an `Application` object that takes a configuration file and scans the packages specified in the configuration.
- Iterate through all classes in the package and extract annotated classes.
- Register the annotated class properties into the factory.
- The factory examines each property, uses reflection to inject values, and produces fully instantiated beans with all properties populated.

## Appendix

## Spring Bean Lifecycle

- ![](https://img-blog.csdn.net/20160417164808359?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)
- ***The general lifecycle of a Spring bean: instantiation → property injection → initialization → usage → destruction.***

- **Instantiation**: The constructor is called to allocate memory for the object.

- **Property population**: Bean properties are set.

- If the bean declares dependencies via `Aware` interfaces, the container injects infrastructure-level dependencies. `Aware` interfaces allow beans to be aware of their own attributes. For example, `BeanNameAware` lets a bean know its name in the container. If a bean implements `BeanFactoryAware`, it can use this to obtain other beans.

- Next, `BeanPostProcessor.postProcessBeforeInitialization` is called. Its main purpose is to add custom processing logic to beans after Spring completes instantiation but before initialization. This is somewhat similar to AOP.

- If the bean implements the `InitializingBean` interface, its `afterPropertiesSet` method is called to perform custom operations after properties have been set.

- The bean's own `init-method` is called to perform initialization work. (At this point the bean is fully initialized.)

- `BeanPostProcessor.postProcessAfterInitialization` is called for custom post-initialization processing.

- After all the above steps, the bean is ready for use in the application.

- ### Destruction Process:

  When a bean is no longer needed, it is destroyed:
  1. If the bean implements `DisposableBean`, its `destroy` method is called.
  2. If a custom destroy method is defined, the custom destroy method is called.

> Partially referenced from https://4m.cn/F26GP
