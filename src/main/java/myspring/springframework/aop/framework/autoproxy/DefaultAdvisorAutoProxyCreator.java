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
 * 基于当前 BeanFactory 中的所有候选者创建 AOP 代理的 BeanPostProcessor 实现。
 * @author Ryan
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    private final Set<Object> earlyProxyReferences = Collections.synchronizedSet(new HashSet<>());

    /**
     * 设置工厂
     *
     * @param beanFactory 工厂
     * @throws BeansException 异常
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    /**
     * 实例化bean前处理
     * @param beanClass class
     * @param beanName  对象名
     * @return 对象
     * @throws BeansException 异常
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
     * 在 Bean 对象执行初始化方法之前，执行此方法
     *
     * @param bean     对象
     * @param beanName bean名字
     * @return object
     * @throws BeansException 异常
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (!earlyProxyReferences.contains(beanName)){
            return wrapIfNecessary(bean, beanName);
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
            return null;
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
     * 在 Bean 对象实例化完成后，设置属性操作之前执行此方法
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
