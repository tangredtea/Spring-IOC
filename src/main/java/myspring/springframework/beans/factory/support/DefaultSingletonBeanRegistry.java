package myspring.springframework.beans.factory.support;

import myspring.springframework.beans.BeansException;
import myspring.springframework.beans.factory.DisposableBean;
import myspring.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ryan
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 一级缓存，普通对象
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    /**
     * 二级缓存，提前暴露的对象，没有完全实例化的对象
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>();

    /**
     * 三级缓存，存放代理对象
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();

    protected static final Object NULL_OBJECT = new Object();

    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    /**
     * 得到单例对象
     *
     * @param beanName 单例对象名
     * @return object
     */
    @Override
    public Object getSingleton(String beanName) {
        Object o = singletonObjects.get(beanName);
        if (null == o){
            o = earlySingletonObjects.get(beanName);
            if (null == o){
                ObjectFactory<?> objectFactory = singletonFactories.get(beanName);
                if (objectFactory != null){
                    o = objectFactory.getObject();
                    earlySingletonObjects.put(beanName, o);
                    singletonFactories.remove(beanName);
                }
            }
        }
        return o;
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory){
        if (!this.singletonObjects.containsKey(beanName)){
            this.singletonFactories.put(beanName, singletonFactory);
            this.earlySingletonObjects.remove(beanName);
        }
    }

    protected void addSingleton(String beanName, Object singleObject){
        singletonObjects.put(beanName, singleObject);
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    public void destroySingletons() {
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject){
        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }
}
