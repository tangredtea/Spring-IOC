package myspring.springframework.beans.factory.support;

import myspring.springframework.beans.factory.BeansException;
import myspring.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Ryan
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy{
    /**
     * 实例化策略
     *
     * @param beanDefinition 定义
     * @param beanName       名字
     * @param ctor           构造器
     * @param args           参数
     * @return object
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) {
        Class clazz = beanDefinition.getBeanClass();
        try {
            if (null != ctor){
                return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            }else {
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new BeansException("Failed to instantiate [" + clazz.getName() + "]", e);
        }
    }
}
