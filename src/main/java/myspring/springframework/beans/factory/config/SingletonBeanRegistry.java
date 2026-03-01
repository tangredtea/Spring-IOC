package myspring.springframework.beans.factory.config;

/**
 * Interface that defines a registry for shared bean instances.
 *
 * @author Ryan
 */
public interface SingletonBeanRegistry {

    /**
     * Return the singleton object registered under the given name.
     *
     * @param beanName the name of the bean to look for
     * @return the registered singleton object, or {@code null} if none found
     */
    Object getSingleton(String beanName);

    /**
     * Register the given existing object as singleton in the bean registry.
     *
     * @param beanName        the name of the bean
     * @param singletonObject the existing singleton object
     */
    void registerSingleton(String beanName, Object singletonObject);
}
