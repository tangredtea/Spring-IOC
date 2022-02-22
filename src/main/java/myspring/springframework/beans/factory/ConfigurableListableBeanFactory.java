package myspring.springframework.beans.factory;

import myspring.springframework.beans.factory.config.AutowireCapableBeanFactory;
import myspring.springframework.beans.factory.config.BeanDefinition;
import myspring.springframework.beans.factory.config.BeanPostProcessor;
import myspring.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author Ryan
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    /**
     * 得到beanDefinition
     * @param beanName beanName
     * @return beanDefinition
     * @throws BeansException 异常
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 提前单例初始化
     * @throws BeansException 异常
     */
    void preInstantiateSingletons() throws BeansException;

    /**
     * 添加对象处置器
     * @param beanPostProcessor 对象处理器
     */
    @Override
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}

