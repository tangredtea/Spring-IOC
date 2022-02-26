package myspring.springframework.beans.factory;

import myspring.springframework.beans.BeansException;

/**
 * @author Ryan
 */
public interface ObjectFactory<T> {

    /**
     * 得到对象
     * @return bean
     * @throws BeansException 异常
     */
    T getObject() throws BeansException;
}
