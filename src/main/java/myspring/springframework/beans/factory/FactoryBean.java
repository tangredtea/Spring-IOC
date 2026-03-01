package myspring.springframework.beans.factory;

/**
 * Interface to be implemented by objects used in a BeanFactory which are themselves factories
 * for individual objects.
 *
 * @author Ryan
 */
public interface FactoryBean<T> {
    /**
     * Return an instance of the object managed by this factory.
     *
     * @return an instance of the bean
     * @throws Exception in case of creation errors
     */
    T getObject() throws Exception;

    /**
     * Return the type of object that this FactoryBean creates.
     *
     * @return the type of object, or {@code null} if not known at the time of the call
     */
    Class<?> getObjectType();

    /**
     * Is the object managed by this factory a singleton?
     *
     * @return whether the exposed object is a singleton
     */
    boolean isSingleton();

}
