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
}
