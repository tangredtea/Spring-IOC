package myspring.springframework.beans.factory.config;

import myspring.springframework.beans.PropertyValues;
import myspring.springframework.beans.BeansException;

/**
 * @author Ryan
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{

    /**
     * 实例化bean前处理
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


    /**
     * Perform operations after the bean has been instantiated, via a constructor or factory method,
     * but before Spring property population (from explicit properties or autowiring) occurs.
     * <p>This is the ideal callback for performing field injection on the given bean instance.
     * See Spring's own
     * for a typical example.
     * <p>
     * 在 Bean 对象执行初始化方法之后，执行此方法
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

    /**
     * 在 Spring 中由 SmartInstantiationAwareBeanPostProcessor#getEarlyBeanReference 提供
     * @param bean bean对象
     * @param beanName bean名字
     * @return object
     */
    default Object getEarlyBeanReference(Object bean, String beanName) {
        return bean;
    }
}
