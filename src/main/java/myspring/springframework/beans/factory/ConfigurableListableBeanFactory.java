package myspring.springframework.beans.factory;

import myspring.springframework.beans.factory.config.AutowireCapableBeanFactory;
import myspring.springframework.beans.factory.config.BeanDefinition;
import myspring.springframework.beans.factory.config.BeanPostProcessor;
import myspring.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author Ryan
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    /**
     * 得到beanDefinition
     * @param beanName beanName
     * @return beanDefinition
     * @throws BeansException 异常
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void preInstantiateSingletons() throws BeansException;

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}

