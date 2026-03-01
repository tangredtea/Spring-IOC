package myspring.springframework.beans.factory.support;

import myspring.springframework.beans.BeansException;

/**
 * @author Ryan
 */
public interface ObjectFactory<T> {

    /**
     * Get the object managed by this factory
     * @return the object instance
     * @throws BeansException if an error occurs while creating the object
     */
    T getObject() throws BeansException;
}
