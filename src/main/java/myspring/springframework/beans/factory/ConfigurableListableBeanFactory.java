package myspring.springframework.beans.factory;

import myspring.springframework.beans.BeansException;
import myspring.springframework.beans.factory.config.AutowireCapableBeanFactory;
import myspring.springframework.beans.factory.config.BeanDefinition;
import myspring.springframework.beans.factory.config.BeanPostProcessor;
import myspring.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Configuration interface to be implemented by most listable bean factories.
 *
 * @author Ryan
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    /**
     * Return the BeanDefinition for the given bean name.
     *
     * @param beanName the name of the bean
     * @return the BeanDefinition for the given name
     * @throws BeansException if no such bean definition is found
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * Ensure that all non-lazy-init singletons are instantiated.
     *
     * @throws BeansException if any bean could not be created
     */
    void preInstantiateSingletons() throws BeansException;

    /**
     * Add a BeanPostProcessor that will get applied to beans created by this factory.
     *
     * @param beanPostProcessor the post-processor to register
     */
    @Override
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}

