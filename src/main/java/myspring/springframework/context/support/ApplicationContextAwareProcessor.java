package myspring.springframework.context.support;

import myspring.springframework.beans.BeansException;
import myspring.springframework.beans.factory.config.BeanPostProcessor;
import myspring.springframework.context.ApplicationContext;
import myspring.springframework.context.ApplicationContextAware;

/**
 * @author Ryan
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    /**
     * Execute this method before the bean object's initialization method is invoked
     *
     * @param bean     the bean object
     * @param beanName the bean name
     * @return object
     * @throws BeansException exception
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware){
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    /**
     * Execute this method after the bean object's initialization method is invoked
     *
     * @param bean     the bean object
     * @param beanName the bean name
     * @return object
     * @throws BeansException exception
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
