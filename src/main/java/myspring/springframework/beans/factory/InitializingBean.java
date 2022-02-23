package myspring.springframework.beans.factory;

/**
 * @author Ryan
 */
public interface InitializingBean {

    /**
     * Bean处理了属性填充
     */
    void afterPropertiesSet();

}
