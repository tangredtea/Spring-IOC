package myspring.springframework.beans.factory;

/**
 * Interface to be implemented by beans that want to be aware of their bean name in a factory.
 *
 * @author Ryan
 */
public interface BeanNameAware extends Aware {

    /**
     * Set the name of the bean in the factory that created this bean.
     *
     * @param name the name of the bean in the factory
     */
    void setBeanName(String name);
}
