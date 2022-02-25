package myspring.springframework.context.support;

import myspring.springframework.beans.BeansException;
import myspring.springframework.beans.factory.ConfigurableListableBeanFactory;
import myspring.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author Ryan
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    /**
     * 加载beanDefinitions
     * @param beanFactory 对象工厂
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

}
