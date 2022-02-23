# Spring手写实现笔记

> 本篇文章并不是教你如何基于XML注解实现Spring，仅仅是本人手写实现Spring XML注解注入的时候一点心得，如果发现有错误，还望告知，github链接：https://github.com/laowenruo/Spring-IOC  （目前仅仅实现了XML，之后还会实现其他方式）

## 基于XML注解实现

### 原理

+ IOC的作用就是把每个bean之间的关系交给第三方容器进行管理，bean的初始化等交给容器处理，即控制反转

+ 所有配置文件只要是配置了全路径，我们就可以理解为其是反射得到的（如：spring.xml中配置的bean中的class属性）

+ SpringIOC的XML版本采用的是dom4j+反射技术实现的

+ 反射的构造对象，肯定会走无参构造函数的。（无论构造函数是否私有）

### 核心实现

#### 定义ApplicationContext

> 因为我们使用Spring的Xml注入的时候，我们是通过ApplicationContext，即应用上下文来加载Xml后获取对象的，所以我们第一步先定义一个ApplicationContext的接口（为什么要定义成接口，主要是为了类的设计--单一职责原则）

```java
public interface ApplicationContext{
    /**
     * 根据类名获取对象，即ByClass
     * @param clazz class
     * @return object
     * @throws Exception errors
     */
    Object getBean(Class clazz) throws Exception;

    /**
     * 根据名字获取对象，即ByName
     * @param beanName name
     * @return object
     * @throws Exception errors
     */
    Object getBean(String beanName) throws Exception;
}
```

####  定义AbstractApplicationContext

> 这里实现得就有点像代理模式了，并且也要引入一个BeanFactory，因为我们获取的对象都在BeanFactory里面构造，说到这里，我们可能会想到了部分原理，即ApplicationContext传入一个XML文件----XML文件转换为Resource流-----初始化工厂------读取Resource流中配置信息到BeanDefinition-----注册到工厂类----由之前的工厂类创建Bean对象，并且设置各种属性等
```java
public class AbstractApplicationContext implements ApplicationContext{
    public BeanFactory beanFactory;  //工厂类，实现了工厂模式
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
### 定义BeanDefinition

```java

public class BeanDefinition {
    private Object bean;  //实例化后的对象
    private Class beanClass;
    private String beanClassName;
    private Boolean singleton; //是否为单例模式
    private PropertyValues propertyValues;   //这个也就是属性的键值对了

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

## 定义ClassPathXmlApplicationContext

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

## 定义XmlBeanDefinitionReader

```java

/**
 * XML配置文件形式的Bean定义读取类
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
    // 解析xml document并注册bean
    registerBeanDefinitions(document);
    inputStream.close();
  }

  public void registerBeanDefinitions(Document document) {
    Element root = document.getDocumentElement();
    // 从文件根递归解析
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
          // 优先进行值注入
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



## 总结

+ ClassPathXmlApplication传入xml文件的路径，并且在构造函数中调用refresh方法

+ 在这个方法中由AbstractBeanFactory定义了一个工厂类，并且调用了obtainBeanFactory方法，在方法中调用了XmlBeanDefinitionReader类，这个类将XML转换成Resource流，并且读取了其中的key和value值，value值就是BeanDefinition

+ 由AutowiredCapableBeanFactory（自动装配工厂类）定义一个工厂，将上述的Key和Value注册到工厂中并且返回到上面定义的工厂类，即将BeanDefinition注册到工厂类中

+ 最后调用prepareBeanFactory方法，层层嵌套后是调用doCreateBean方法，将对象中的属性注入对象中，返回Bean到工厂中，此时BeanDefinition中的bean中就是一个实例化后、具有属性设置的对象了

+ 之后，你就可以通过ByName或者ByClass来获取你的对象了

  > 部分内容参考来自https://4m.cn/F26GP