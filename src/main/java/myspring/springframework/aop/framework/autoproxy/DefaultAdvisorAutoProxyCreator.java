package myspring.springframework.aop.framework.autoproxy;

import myspring.springframework.aop.*;
import myspring.springframework.aop.aspectj.AspectjExpressionPointcutAdvisor;
import myspring.springframework.aop.framework.ProxyFactory;
import myspring.springframework.beans.PropertyValues;
import myspring.springframework.beans.factory.BeanFactory;
import myspring.springframework.beans.factory.BeanFactoryAware;
import myspring.springframework.beans.BeansException;
import myspring.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import myspring.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * BeanPostProcessor implementation that creates AOP proxies based on all candidate advisors in the current BeanFactory.
 * @author Ryan
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    private final Set<Object> earlyProxyReferences = Collections.synchronizedSet(new HashSet<>());

    /**
     * Set the bean factory
     *
     * @param beanFactory the bean factory
     * @throws BeansException exception
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    /**
     * Pre-instantiation processing of bean
     * @param beanClass the class of the bean
     * @param beanName  the name of the bean
     * @return the bean object
     * @throws BeansException exception
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    /**
     * Execute this method before the bean object's initialization method is invoked
     *
     * @param bean     the bean object
     * @param beanName the name of the bean
     * @return object
     * @throws BeansException exception
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * Execute this method after the bean object's initialization method is invoked
     *
     * @param bean     the bean object
     * @param beanName the name of the bean
     * @return object
     * @throws BeansException exception
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!earlyProxyReferences.contains(beanName)) {
            return wrapIfNecessary(bean, beanName);
        }
        return bean;
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) {
        earlyProxyReferences.add(beanName);
        return wrapIfNecessary(bean, beanName);
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    private Object wrapIfNecessary(Object bean, String name) {
        Class<?> beanClass = bean.getClass();
        if (isInfrastructureClass(beanClass)){
            return bean;
        }
        Collection<AspectjExpressionPointcutAdvisor> values = beanFactory.getBeansOfType(AspectjExpressionPointcutAdvisor.class).values();
        for (AspectjExpressionPointcutAdvisor value : values) {
            ClassFilter classFilter = value.getPointcut().getClassFilter();
            if (!classFilter.matches(beanClass)){
                continue;
            }
            AdvisedSupport advisedSupport = new AdvisedSupport();
            TargetSource targetSource = new TargetSource(bean);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) value.getAdvice());
            advisedSupport.setMethodMatcher(value.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(true);
            return new ProxyFactory(advisedSupport).getProxy();
        }
        return bean;
    }


    /**
     * Post-process the given property values before the factory applies them
     * to the given bean. Allows for checking whether all dependencies have been
     * satisfied, for example based on a "Required" annotation on bean property setters.
     * <p>
     * Executed after the bean object has been instantiated, but before property values are set
     *
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }
}
