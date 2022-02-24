package myspring.springframework.beans.factory.config;

/**
 * 单例注册
 * @author Ryan
 */
public interface SingletonBeanRegistry {

    /**
     * 得到单例对象
     * @param beanName 单例对象名
     * @return object
     */
    Object getSingleton(String beanName);

    /**
     * 注册单例
     * @param beanName 单例对象名
     * @param singletonObject 单例对象
     */
    void registerSingleton(String beanName, Object singletonObject);
}
