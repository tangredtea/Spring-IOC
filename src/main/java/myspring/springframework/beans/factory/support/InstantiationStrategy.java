package myspring.springframework.beans.factory.support;

import myspring.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @author Ryan
 */
public interface InstantiationStrategy {

    /**
     * Instantiate a bean using the given strategy
     * @param beanDefinition the bean definition
     * @param beanName the name of the bean
     * @param ctor the constructor to use
     * @param args the constructor arguments
     * @return the instantiated object
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<?> ctor, Object[] args);
}
