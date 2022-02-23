package myspring.springframework.beans.factory;

/**
 * @author Ryan
 */
public interface BeanFactoryAware extends Aware{

    /**
     * 设置工厂
     * @param beanFactory 工厂
     * @throws BeansException 异常
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
