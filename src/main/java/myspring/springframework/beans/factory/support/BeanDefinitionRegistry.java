package myspring.springframework.beans.factory.support;

import myspring.springframework.beans.BeansException;
import myspring.springframework.beans.factory.config.BeanDefinition;

/**
 * @author Ryan
 */
public interface BeanDefinitionRegistry {

    /**
     * Register a BeanDefinition in the registry
     * @param beanName the name of the bean
     * @param beanDefinition the bean definition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * Look up a BeanDefinition by bean name
     * @param beanName the name of the bean
     * @return beanDefinition
     * @throws BeansException if the bean definition cannot be found
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * Check whether the registry contains a BeanDefinition with the given name
     * @param beanName the name of the bean
     * @return true if the registry contains a matching BeanDefinition
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * Return the names of all beans defined in this registry
     * @return the array of bean names
     */
    String[] getBeanDefinitionNames();
}
