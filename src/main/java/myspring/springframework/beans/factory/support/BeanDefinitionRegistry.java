package myspring.springframework.beans.factory.support;

import myspring.springframework.beans.BeansException;
import myspring.springframework.beans.factory.config.BeanDefinition;

/**
 * @author Ryan
 */
public interface BeanDefinitionRegistry {

    /**
     * 向注册表中注册 BeanDefinition
     * @param beanName 名字
     * @param beanDefinition 定义
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 使用Bean名称查询BeanDefinition
     * @param beanName 名字
     * @return beanDefinition
     * @throws BeansException 异常
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 判断是否包含指定名称的BeanDefinition
     * @param beanName 名字
     * @return 布尔值
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 返回注册表中的所有names
     * @return name
     */
    String[] getBeanDefinitionNames();
}
