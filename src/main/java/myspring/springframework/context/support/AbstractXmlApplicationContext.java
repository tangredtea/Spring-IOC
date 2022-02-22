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
     * 得到配置文件路径
     * @return 字符串数组
     */
    protected abstract String[] getConfigLocations();

}
