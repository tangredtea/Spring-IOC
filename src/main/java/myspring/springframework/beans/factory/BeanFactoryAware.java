package myspring.springframework.beans.factory;

import myspring.springframework.beans.BeansException;

/**
 * Interface to be implemented by beans that wish to be aware of their owning BeanFactory.
 *
 * @author Ryan
 */
public interface BeanFactoryAware extends Aware {

    /**
     * Supply the owning factory to a bean instance.
     *
     * @param beanFactory owning BeanFactory
     * @throws BeansException if initialization fails
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
