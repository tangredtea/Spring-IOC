package myspring.springframework.context;

import myspring.springframework.factory.AbstractBeanFactory;
import myspring.springframework.factory.AutowiredCapableBeanFactory;
import myspring.springframework.reader.XmlBeanDefinitionReader;
import myspring.springframework.entity.BeanDefinition;
import myspring.springframework.io.ResourceLoader;

import java.util.Map;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    private final Object startupShutdownMonitor = new Object();
    private String location;

    public ClassPathXmlApplicationContext(String location) throws Exception {
        super();
        this.location = location;
        refresh();
    }

    /**
     * 最关键的方法
     * @throws Exception
     */
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

    public void addNewBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception {
        XmlBeanDefinitionReader.processAnnotationProperty(beanDefinition.getBeanClass(), beanDefinition);
        beanFactory.registerBeanDefinition(name, beanDefinition);
    }

    public void refreshBeanFactory() throws Exception {
        prepareBeanFactory((AbstractBeanFactory) beanFactory);
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
