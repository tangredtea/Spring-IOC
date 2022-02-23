package myspring.springframework.beans.factory;

/**
 * @author Ryan
 */
public interface BeanNameAware extends Aware{

    /**
     * 设置beanName
     * @param name 名字
     */
    void setBeanName(String name);
}
