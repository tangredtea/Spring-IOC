package myspring.springframework.beans.factory.support;

import myspring.springframework.beans.factory.BeanFactory;
import myspring.springframework.beans.factory.BeansException;
import myspring.springframework.beans.factory.config.BeanDefinition;
import myspring.springframework.beans.factory.config.BeanPostProcessor;
import myspring.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    /** BeanPostProcessors to apply in createBean */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    /**
     * 获取bean
     *
     * @param name bean名字
     * @return object
     * @throws BeansException 异常
     */
    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    /**
     * 获取bean
     *
     * @param name 名字
     * @param args 参数
     * @return object
     * @throws BeansException 异常
     */
    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    /**
     * 获取对象
     *
     * @param name         对象名
     * @param requiredType 需要的class
     * @return bean
     * @throws BeansException
     */
    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    /**
     * 得到单例对象
     *
     * @param beanName 单例对象名
     * @return object
     */
    @Override
    public Object getSingleton(String beanName) {
        return super.getSingleton(beanName);
    }

    protected <T> T doGetBean(final String name, final Object[] args){
        Object bean = getSingleton(name);
        if (bean != null){
            return (T) bean;
        }
        return (T) createBean(name, getBeanDefinition(name), args);
    }

    /**
     * 得到bean定义
     * @param beanName 名字
     * @return beanDefinition
     * @throws BeansException 异常
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 创造Bean
     * @param beanName 名字
     * @param beanDefinition 定义
     * @param args 参数
     * @return object
     * @throws BeansException 异常
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws  BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors(){
        return this.beanPostProcessors;
    }
}
