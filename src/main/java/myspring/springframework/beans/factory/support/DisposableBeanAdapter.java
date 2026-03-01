package myspring.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import myspring.springframework.beans.BeansException;
import myspring.springframework.beans.factory.DisposableBean;
import myspring.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Ryan
 */
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;
    private final String beanName;
    private final String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    /**
     * Invoke the destroy method on the bean
     */
    @Override
    public void destroy() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Destroy via DisposableBean interface
        if (bean instanceof DisposableBean){
            ((DisposableBean) bean).destroy();
        }
        // Destroy via configured destroy-method
        if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof  DisposableBean && "destroy".equals(this.destroyMethodName))){
            Method method = bean.getClass().getMethod(destroyMethodName);
            method.invoke(bean);
        }
    }
}
