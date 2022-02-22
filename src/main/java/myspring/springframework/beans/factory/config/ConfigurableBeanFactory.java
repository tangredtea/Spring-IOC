package myspring.springframework.beans.factory.config;

import myspring.springframework.beans.factory.HierarchicalBeanFactory;

/**
 * @author Ryan
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 添加对象处理
     * @param beanPostProcessor 对象处理器
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
