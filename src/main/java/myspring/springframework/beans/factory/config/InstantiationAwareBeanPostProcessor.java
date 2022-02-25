package myspring.springframework.beans.factory.config;

import myspring.springframework.beans.factory.BeansException;

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
}
