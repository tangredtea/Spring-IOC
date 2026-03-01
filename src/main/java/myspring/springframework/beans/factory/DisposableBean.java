package myspring.springframework.beans.factory;

import java.lang.reflect.InvocationTargetException;

/**
 * Interface to be implemented by beans that want to release resources on destruction.
 *
 * @author Ryan
 */
public interface DisposableBean {

    /**
     * Invoked by the containing BeanFactory on destruction of a bean.
     *
     * @throws NoSuchMethodException     if a destroy method is not found
     * @throws InvocationTargetException if the destroy method throws an exception
     * @throws IllegalAccessException    if the destroy method is not accessible
     */
    void destroy() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
