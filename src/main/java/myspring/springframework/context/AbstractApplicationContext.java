package myspring.springframework.context;

import myspring.springframework.factory.BeanFactory;

public abstract class AbstractApplicationContext implements ApplicationContext {
    /**
     * 类似代理模式，由工厂得到对象
     */
    BeanFactory beanFactory;

    @Override
    public Object getBean(Class clazz) throws Exception {
        return beanFactory.getBean(clazz);
    }

    @Override
    public Object getBean(String beanName) throws Exception {
        return beanFactory.getBean(beanName);
    }
}
