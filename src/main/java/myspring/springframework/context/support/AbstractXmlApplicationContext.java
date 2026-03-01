package myspring.springframework.context.support;


import myspring.springframework.beans.factory.support.DefaultListableBeanFactory;
import myspring.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author Ryan
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (null != configLocations){
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    /**
     * Get the configuration file paths
     * @return string array
     */
    protected abstract String[] getConfigLocations();

}
