package myspring.springframework.beans.factory.config;

import myspring.springframework.beans.factory.BeanFactory;
import myspring.springframework.beans.BeansException;

/**
 * Extension of BeanFactory that provides auto-wiring capabilities.
 *
 * @author Ryan
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * Apply BeanPostProcessors to the given existing bean instance, invoking their
     * postProcessBeforeInitialization methods.
     *
     * @param existingBean the existing bean instance
     * @param beanName     the name of the bean
     * @return the bean instance to use, either the original or a wrapped one
     * @throws BeansException if any post-processing failed
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * Apply BeanPostProcessors to the given existing bean instance, invoking their
     * postProcessAfterInitialization methods.
     *
     * @param existingBean the existing bean instance
     * @param beanName     the name of the bean
     * @return the bean instance to use, either the original or a wrapped one
     * @throws BeansException if any post-processing failed
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;

}