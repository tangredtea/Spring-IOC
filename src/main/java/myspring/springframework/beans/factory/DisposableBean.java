package myspring.springframework.beans.factory;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Ryan
 */
public interface DisposableBean {

    /**
     * 销毁方法
     */
    void destroy() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
