package myspring.springframework.beans.factory;

import myspring.springframework.beans.BeansException;

/**
 * The root interface for accessing a Spring bean container.
 *
 * @author Ryan
 */
public interface BeanFactory {

    /**
     * Return an instance of the specified bean.
     *
     * @param name the name of the bean to retrieve
     * @return an instance of the bean
     * @throws BeansException if the bean could not be obtained
     */
    Object getBean(String name) throws BeansException;

    /**
     * Return an instance of the specified bean, with explicit constructor arguments.
     *
     * @param name the name of the bean to retrieve
     * @param args arguments to use when creating a bean instance
     * @return an instance of the bean
     * @throws BeansException if the bean could not be obtained
     */
    Object getBean(String name, Object... args) throws BeansException;

    /**
     * Return an instance of the specified bean, ensuring it is of the given type.
     *
     * @param name         the name of the bean to retrieve
     * @param requiredType type the bean must match
     * @param <T>          the type of the bean
     * @return an instance of the bean
     * @throws BeansException if the bean could not be obtained
     */
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    /**
     * Return the bean instance that uniquely matches the given type.
     *
     * @param requiredType type the bean must match
     * @param <T>          the type of the bean
     * @return an instance of the bean
     * @throws BeansException if the bean could not be obtained
     */
    <T> T getBean(Class<T> requiredType) throws BeansException;

    /**
     * Check if this factory contains a bean definition or a singleton instance with the given name.
     *
     * @param name the name of the bean to query
     * @return whether a bean with the given name is present
     */
    boolean containsBean(String name);

}
