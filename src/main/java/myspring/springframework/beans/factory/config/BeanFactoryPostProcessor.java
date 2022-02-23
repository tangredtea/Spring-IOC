package myspring.springframework.beans.factory.config;

import myspring.springframework.beans.factory.BeansException;
import myspring.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * @author Ryan
 */
public interface BeanFactoryPostProcessor {
    /**
     * 在所有的 BeanDefinition 加载完成后，实例化 Bean 对象之前，提供修改 BeanDefinition 属性的机制
     * @param beanFactory 容器
     * @throws BeansException 异常
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
