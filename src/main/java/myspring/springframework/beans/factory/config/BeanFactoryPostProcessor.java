package myspring.springframework.beans.factory.config;

import myspring.springframework.beans.BeansException;
import myspring.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * Factory hook that allows for custom modification of an application context's bean definitions,
 * adapting the bean property values of the context's underlying bean factory.
 *
 * @author Ryan
 */
public interface BeanFactoryPostProcessor {
    /**
     * Modify the application context's internal bean factory after its standard initialization.
     * All bean definitions will have been loaded, but no beans will have been instantiated yet.
     *
     * @param beanFactory the bean factory used by the application context
     * @throws BeansException if an error occurs
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
