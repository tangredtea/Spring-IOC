package myspring.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import myspring.springframework.beans.factory.BeansException;
import myspring.springframework.beans.factory.DisposableBean;
import myspring.springframework.beans.factory.InitializingBean;
import myspring.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Ryan
 */
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;
    private final String beanName;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    /**
     * 销毁方法
     */
    @Override
    public void destroy() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Disposable接口销毁
        if (bean instanceof DisposableBean){
            ((DisposableBean) bean).destroy();
        }
        // 配置信息 destroy-method销毁
        if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof  DisposableBean && "destroy".equals(this.destroyMethodName))){
            Method method = bean.getClass().getMethod(destroyMethodName);
            if (null == method){
                throw new BeansException("Could not find a destroy method named '" + destroyMethodName + "' on bean with name" + beanName + "'");
            }
            method.invoke(bean);
        }
    }
}
