package myspring.springframework.beans.factory.config;

import myspring.springframework.beans.PropertyValues;
import myspring.springframework.beans.BeansException;

/**
 * @author Ryan
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{

    /**
     * 初始化bean处理
     * @param beanClass class
     * @param beanName 对象名
     * @return 对象
     * @throws BeansException 异常
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    /**
     * Post-process the given property values before the factory applies them
     * to the given bean. Allows for checking whether all dependencies have been
     * satisfied, for example based on a "Required" annotation on bean property setters.
     *
     * 在 Bean 对象实例化完成后，设置属性操作之前执行此方法
     *
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException;
}
