package myspring.springframework.beans.factory.config;

import myspring.springframework.beans.BeansException;

/**
 * Factory hook that allows for custom modification of new bean instances.
 *
 * @author Ryan
 */
public interface BeanPostProcessor {

    /**
     * Apply this BeanPostProcessor to the given new bean instance <i>before</i> any bean
     * initialization callbacks (like InitializingBean's afterPropertiesSet or a custom init-method).
     *
     * @param bean     the new bean instance
     * @param beanName the name of the bean
     * @return the bean instance to use, either the original or a wrapped one
     * @throws BeansException if an error occurs
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * Apply this BeanPostProcessor to the given new bean instance <i>after</i> any bean
     * initialization callbacks (like InitializingBean's afterPropertiesSet or a custom init-method).
     *
     * @param bean     the new bean instance
     * @param beanName the name of the bean
     * @return the bean instance to use, either the original or a wrapped one
     * @throws BeansException if an error occurs
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
