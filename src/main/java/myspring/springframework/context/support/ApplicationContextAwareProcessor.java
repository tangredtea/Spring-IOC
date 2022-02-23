package myspring.springframework.context.support;

import myspring.springframework.beans.factory.BeansException;
import myspring.springframework.beans.factory.config.BeanPostProcessor;
import myspring.springframework.context.ApplicationContext;
import myspring.springframework.context.ApplicationContextAware;

/**
 * @author Ryan
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法
     *
     * @param bean     对象
     * @param beanName bean名字
     * @return object
     * @throws BeansException 异常
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware){
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    /**
     * 在 Bean 对象执行初始化方法之后，执行此方法
     *
     * @param bean     对象
     * @param beanName bean名字
     * @return object
     * @throws BeansException 异常
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
