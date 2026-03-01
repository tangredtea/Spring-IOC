package myspring.springframework.beans.factory;

import myspring.springframework.beans.BeansException;

import java.util.Map;

/**
 * Extension of BeanFactory to be implemented by bean factories that can enumerate all their bean instances.
 *
 * @author Ryan
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * Return the bean instances that match the given type (including subclasses).
     *
     * @param type the class or interface to match
     * @param <T>  the type of the beans
     * @return a Map with the matching beans, containing the bean names as keys and bean instances as values
     * @throws BeansException if a bean could not be created
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * Return the names of all beans defined in this factory.
     *
     * @return the names of all beans defined in this factory
     */
    String[] getBeanDefinitionNames();

}