# Handwritten Spring Implementation Notes

> This article is not a step-by-step tutorial on implementing Spring with XML-based injection. It is simply a collection of insights I gathered while hand-writing an XML-based Spring injection mechanism. If you find any errors, please let me know. GitHub link: https://github.com/laowenruo/Spring-IOC

## XML-Based Injection

### Principles

+ The purpose of IOC is to delegate the management of relationships between beans to a third-party container. Bean initialization is handled by the container — this is known as Inversion of Control.

+ Whenever a configuration file specifies a fully qualified class path, we can assume it is resolved via reflection (e.g., the `class` attribute of `<bean>` in `spring.xml`).

+ The XML version of Spring IOC is implemented using dom4j + reflection.

+ Reflection-based object construction always invokes the no-arg constructor (regardless of its access modifier).

### Core Implementation

#### Defining ApplicationContext

> When using Spring's XML injection, we obtain objects through the `ApplicationContext` (application context) after loading the XML. So the first step is to define an `ApplicationContext` interface (it's defined as an interface to follow the Single Responsibility Principle).

```java
public interface ApplicationContext{
    /**
     * Get bean by class
     * @param clazz class
     * @return object
     * @throws Exception errors
     */
    Object getBean(Class clazz) throws Exception;

    /**
     * Get bean by name
     * @param beanName name
     * @return object
     * @throws Exception errors
     */
    Object getBean(String beanName) throws Exception;
}
```

#### Defining AbstractApplicationContext

> This is somewhat similar to the Proxy pattern. We also need to introduce a `BeanFactory`, since all objects are constructed within it. At this point, we can see part of the overall flow: `ApplicationContext` receives an XML file path → XML is converted to a `Resource` stream → factory is initialized → `Resource` stream configuration is read into `BeanDefinition` → registered in the factory → factory creates Bean objects and sets their properties.

```java
public class AbstractApplicationContext implements ApplicationContext{
    public BeanFactory beanFactory;  // Factory class, implementing the Factory pattern
    @Override
   public Object getBean(Class clazz) throws Exception {
        return beanFactory.getBean(clazz);
    }

    @Override
    public Object getBean(String beanName) throws Exception {
        return beanFactory.getBean(beanName);
    }
}
```

### Defining BeanDefinition

```java

public class BeanDefinition {
    private Object bean;  // The instantiated object
    private Class beanClass;
    private String beanClassName;
    private Boolean singleton; // Whether this is a singleton
    private PropertyValues propertyValues;   // Property key-value pairs

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
        try {
            this.beanClass = Class.forName(beanClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public PropertyValues getPropertyValues() {
        if(propertyValues == null) {
            propertyValues = new PropertyValues();
        }
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public Boolean isSingleton() {
        return singleton;
    }

    public void setSingleton(Boolean singleton) {
        this.singleton = singleton;
    }
}
```

## Defining ClassPathXmlApplicationContext

```java

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    private final Object startupShutdownMonitor = new Object();
    private final String location;

    public ClassPathXmlApplicationContext(String location) throws Exception {
        super();
        this.location = location;
        refresh();
    }

    public void refresh() throws Exception {
        synchronized (startupShutdownMonitor) {
            AbstractBeanFactory beanFactory = obtainBeanFactory();
            prepareBeanFactory(beanFactory);
            this.beanFactory = beanFactory;
        }
    }

    private void prepareBeanFactory(AbstractBeanFactory beanFactory) throws Exception {
        beanFactory.populateBeans();
    }

    private AbstractBeanFactory obtainBeanFactory() throws Exception {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
        beanDefinitionReader.loadBeanDefinitions(location);
        AbstractBeanFactory beanFactory = new AutowiredCapableBeanFactory();
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : beanDefinitionReader.getRegistry().entrySet()) {
            beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
        }
        return beanFactory;
    }

}
```

## Defining XmlBeanDefinitionReader

```java

/**
 * Bean definition reader for XML configuration files
 *
 * @author ziyang
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

  public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
    super(resourceLoader);
  }

  @Override
  public void loadBeanDefinitions(String location) throws Exception {
    InputStream inputStream = getResourceLoader().getResource(location).getInputStream();
    doLoadBeanDefinitions(inputStream);
  }

  protected void doLoadBeanDefinitions(InputStream inputStream) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = factory.newDocumentBuilder();
    Document document = documentBuilder.parse(inputStream);
    // Parse the XML document and register beans
    registerBeanDefinitions(document);
    inputStream.close();
  }

  public void registerBeanDefinitions(Document document) {
    Element root = document.getDocumentElement();
    // Recursively parse from the document root
    parseBeanDefinitions(root);
  }

  protected void parseBeanDefinitions(Element root) {
    NodeList nodeList = root.getChildNodes();
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      if (node instanceof Element) {
        processBeanDefinition((Element) node);
      }
    }
  }

  protected void processBeanDefinition(Element ele) {
    String name = ele.getAttribute("id");
    String className = ele.getAttribute("class");
    boolean singleton = !ele.hasAttribute("scope") || !"prototype".equals(ele.getAttribute("scope"));
    BeanDefinition beanDefinition = new BeanDefinition();
    processProperty(ele, beanDefinition);
    beanDefinition.setBeanClassName(className);
    beanDefinition.setSingleton(singleton);
    getRegistry().put(name, beanDefinition);
  }

  private void processProperty(Element ele, BeanDefinition beanDefinition) {
    NodeList propertyNode = ele.getElementsByTagName("property");
    for (int i = 0; i < propertyNode.getLength(); i++) {
      Node node = propertyNode.item(i);
      if (node instanceof Element) {
        Element propertyEle = (Element) node;
        String name = propertyEle.getAttribute("name");
        String value = propertyEle.getAttribute("value");
        if (value != null && value.length() > 0) {
          // Value injection takes priority
          beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
        } else {
          String ref = propertyEle.getAttribute("ref");
          if (ref == null || ref.length() == 0) {
            throw new IllegalArgumentException("Configuration problem: <property> element for property '" + name + "' must specify a ref or value");
          }
          BeanReference beanReference = new BeanReference(ref);
          beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
        }
      }
    }
  }

}
```



## Summary

+ `ClassPathXmlApplication` takes the path of an XML file and calls the `refresh` method in its constructor.

+ In this method, an `AbstractBeanFactory` defines a factory, and the `obtainBeanFactory` method is called. Inside, the `XmlBeanDefinitionReader` class converts the XML into a `Resource` stream and reads the key-value pairs, where the values are `BeanDefinition` objects.

+ An `AutowiredCapableBeanFactory` (auto-wiring factory) is created, and the key-value pairs from the previous step are registered into the factory. The factory is then returned as the main factory, meaning all `BeanDefinition` objects are now registered.

+ Finally, the `prepareBeanFactory` method is called. Through nested method calls, it ultimately invokes `doCreateBean`, which injects properties into the objects and returns fully instantiated, property-populated beans to the factory.

+ After that, you can retrieve your objects by name or by class.

  > Partially referenced from https://4m.cn/F26GP
