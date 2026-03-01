package myspring.springframework.beans.factory.support;

import myspring.springframework.beans.BeansException;
import myspring.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Simple instantiation strategy using JDK reflection.
 *
 * @author Ryan
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {
    /**
     * Instantiate a bean using JDK reflection.
     *
     * @param beanDefinition the bean definition
     * @param beanName       the name of the bean
     * @param ctor           the constructor to use
     * @param args           the constructor arguments
     * @return the instantiated object
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<?> ctor, Object[] args) {
        Class<?> clazz = beanDefinition.getBeanClass();
        try {
            if (null != ctor){
                return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            }else {
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new BeansException("Failed to instantiate [" + clazz.getName() + "]", e);
        }
    }
}
