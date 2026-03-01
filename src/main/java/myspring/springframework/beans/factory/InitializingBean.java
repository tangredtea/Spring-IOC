package myspring.springframework.beans.factory;

/**
 * Interface to be implemented by beans that need to react once all their properties have been set.
 *
 * @author Ryan
 */
public interface InitializingBean {

    /**
     * Invoked by the containing BeanFactory after it has set all bean properties.
     */
    void afterPropertiesSet();

}
