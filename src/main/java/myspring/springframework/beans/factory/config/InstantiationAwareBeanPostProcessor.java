package myspring.springframework.beans.factory.config;

import myspring.springframework.beans.PropertyValues;
import myspring.springframework.beans.BeansException;

/**
 * Sub-interface of BeanPostProcessor that adds a before-instantiation callback,
 * an after-instantiation callback, and a property post-processing callback.
 *
 * @author Ryan
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * Apply this BeanPostProcessor <i>before the target bean gets instantiated</i>.
     * The returned bean object may be a proxy to use instead of the target bean.
     *
     * @param beanClass the class of the bean to be instantiated
     * @param beanName  the name of the bean
     * @return the bean object to expose instead of a default instance of the target bean, or {@code null}
     * @throws BeansException if an error occurs
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    /**
     * Post-process the given property values before the factory applies them to the given bean.
     *
     * @param pvs      the property values that the factory is about to apply
     * @param bean     the bean instance created, but whose properties have not yet been set
     * @param beanName the name of the bean
     * @return the actual property values to apply, or {@code null} to skip property population
     * @throws BeansException if an error occurs
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException;

    /**
     * Perform operations after the bean has been instantiated, via a constructor or factory method,
     * but before property population occurs.
     *
     * @param bean     the bean instance that was created
     * @param beanName the name of the bean
     * @return {@code true} if properties should be set on the bean; {@code false} to skip property population
     * @throws BeansException if an error occurs
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

    /**
     * Obtain a reference for early access to the specified bean, typically for resolving a circular reference.
     *
     * @param bean     the raw bean instance
     * @param beanName the name of the bean
     * @return the object to expose as bean reference
     */
    default Object getEarlyBeanReference(Object bean, String beanName) {
        return bean;
    }
}
