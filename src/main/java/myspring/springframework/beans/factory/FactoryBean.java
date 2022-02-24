package myspring.springframework.beans.factory;

/**
 * @author Ryan
 */
public interface FactoryBean<T> {
    /**
     * 得到object
     * @return object
     * @throws Exception 异常
     */
    T getObject() throws Exception;

    /**
     * 得到object type
     * @return class
     */
    Class<?> getObjectType();

    /**
     * 判断是否单例
     * @return 布尔值
     */
    boolean isSingleton();

}
