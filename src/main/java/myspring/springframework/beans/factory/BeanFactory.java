package myspring.springframework.beans.factory;


import myspring.springframework.beans.BeansException;

/**
 * @author Ryan
 */
public interface BeanFactory {

    /**
     * 获取bean
     * @param name bean名字
     * @return object
     * @throws BeansException 异常
     */
    Object getBean(String name) throws BeansException;

    /**
     * 获取bean
     * @param name 名字
     * @param args 参数
     * @return object
     * @throws BeansException 异常
     */
    Object getBean(String name, Object... args) throws BeansException;

    /**
     * 获取对象
     * @param name 对象名
     * @param requiredType 需要的class
     * @param <T> 泛型
     * @return bean
     * @throws BeansException 异常
     */
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

}
