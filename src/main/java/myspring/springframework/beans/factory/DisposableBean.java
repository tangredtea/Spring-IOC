package myspring.springframework.beans.factory;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Ryan
 */
public interface DisposableBean {

    /**
     * 销毁方法
     * @throws NoSuchMethodException 异常
     * @throws InvocationTargetException 异常
     * @throws IllegalAccessException 异常
     */
    void destroy() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
