package myspring.springframework.beans.factory.config;

import myspring.springframework.beans.factory.BeanFactory;
import myspring.springframework.beans.factory.BeansException;

/**
 * @author Ryan
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 执行 BeanPostProcessors 接口实现类的 postProcessBeforeInitialization 方法
     *
     * @param existingBean 对象
     * @param beanName bean名字
     * @return object
     * @throws BeansException 异常
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * 执行 BeanPostProcessors 接口实现类的 postProcessorsAfterInitialization 方法
     *
     * @param existingBean 对象
     * @param beanName bean名字
     * @return object
     * @throws BeansException 异常
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;

}