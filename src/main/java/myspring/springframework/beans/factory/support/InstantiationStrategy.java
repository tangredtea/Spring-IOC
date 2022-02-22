package myspring.springframework.beans.factory.support;

import myspring.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @author Ryan
 */
public interface InstantiationStrategy {

    /**
     * 实例化策略
     * @param beanDefinition 定义
     * @param beanName 名字
     * @param ctor 构造器
     * @param args 参数
     * @return object
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args);
}
