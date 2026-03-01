package myspring.springframework.beans.factory;

/**
 * Callback that allows a bean to be aware of the bean class loader.
 *
 * @author Ryan
 */
public interface BeanClassLoaderAware extends Aware {

    /**
     * Supply the bean class loader to the bean instance.
     *
     * @param classLoader the owning class loader
     */
    void setBeanClassLoader(ClassLoader classLoader);
}
