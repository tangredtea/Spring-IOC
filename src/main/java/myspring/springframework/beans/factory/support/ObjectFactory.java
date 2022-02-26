package myspring.springframework.beans.factory.support;

import myspring.springframework.beans.BeansException;

/**
 * @author Ryan
 */
public interface ObjectFactory<T> {

    /**
     * 得到对象
     * @return object
     * @throws BeansException 异常
     */
    T getObject() throws BeansException;
}
