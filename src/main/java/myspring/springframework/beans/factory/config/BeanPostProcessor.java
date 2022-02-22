package myspring.springframework.beans.factory.config;

import myspring.springframework.beans.factory.BeansException;

/**
 * @author Ryan
 */
public interface BeanPostProcessor {

    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法
     *
     * @param bean 对象
     * @param beanName bean名字
     * @return object
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在 Bean 对象执行初始化方法之后，执行此方法
     *
     * @param bean 对象
     * @param beanName bean名字
     * @return object
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
