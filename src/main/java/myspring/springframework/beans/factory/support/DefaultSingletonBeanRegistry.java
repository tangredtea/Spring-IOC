package myspring.springframework.beans.factory.support;

import myspring.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ryan
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String, Object> singletonObjects = new HashMap<>();

    /**
     * 得到单例对象
     *
     * @param beanName 单例对象名
     * @return object
     */
    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    protected void addSingleton(String beanName, Object singleObject){
        singletonObjects.put(beanName, singleObject);
    }
}
