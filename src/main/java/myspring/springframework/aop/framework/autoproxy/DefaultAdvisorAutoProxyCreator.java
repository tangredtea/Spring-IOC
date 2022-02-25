package myspring.springframework.aop.framework.autoproxy;

import myspring.springframework.aop.*;
import myspring.springframework.aop.aspectj.AspectjExpressionPointcutAdvisor;
import myspring.springframework.aop.framework.ProxyFactory;
import myspring.springframework.beans.factory.BeanFactory;
import myspring.springframework.beans.factory.BeanFactoryAware;
import myspring.springframework.beans.factory.BeansException;
import myspring.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import myspring.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;

/**
 * @author Ryan
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    public void setBeanFactory(DefaultListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
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

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    /**
     * 初始化bean处理
     *
     * @param beanClass class
     * @param beanName  对象名
     * @return 对象
     * @throws BeansException 异常
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
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
            TargetSource targetSource = null;
            try{
                targetSource = new TargetSource(beanClass.getDeclaredConstructor().newInstance());
            }catch (Exception e){
                e.printStackTrace();
            }
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) value.getAdvice());
            advisedSupport.setMethodMatcher(value.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);
            return new ProxyFactory(advisedSupport).getProxy();
        }
        return null;
    }
}
