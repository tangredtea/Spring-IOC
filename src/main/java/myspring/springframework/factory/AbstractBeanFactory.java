package myspring.springframework.factory;

import myspring.springframework.entity.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBeanFactory implements BeanFactory {

    ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public Object getBean(String name) throws Exception {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if(beanDefinition == null) {
            throw new RuntimeException("Unable to find the bean of this name, please check!");
        }
        if(!beanDefinition.isSingleton() || beanDefinition.getBean() == null) {
            return doCreateBean(beanDefinition);
        } else {
            return doCreateBean(beanDefinition);
        }
    }

    @Override
    public Object getBean(Class clazz) throws Exception {
        BeanDefinition beanDefinition = null;
        for(Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            Class tmpClass = entry.getValue().getBeanClass();
            if(tmpClass == clazz || clazz.isAssignableFrom(tmpClass)) {
                beanDefinition = entry.getValue();
            }
        }
        if(beanDefinition == null) {
            throw new RuntimeException("Unable to find the bean of this class, please check!");
        }
        if(!beanDefinition.isSingleton() || beanDefinition.getBean() == null) {
            return doCreateBean(beanDefinition);
        } else {
            return beanDefinition.getBean();
        }
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
    }

    /**
     * 创建Bean实例
     * @param beanDefinition Bean定义对象
     * @return Bean实例对象
     * @throws Exception 可能出现的异常
     */
    abstract Object doCreateBean(BeanDefinition beanDefinition) throws Exception;

    public void populateBeans() throws Exception {
        for(Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            doCreateBean(entry.getValue());
        }
    }
}
