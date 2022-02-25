package myspring.springframework.context.event;

import myspring.springframework.beans.factory.BeanFactory;
import myspring.springframework.beans.factory.BeanFactoryAware;
import myspring.springframework.beans.BeansException;
import myspring.springframework.context.AbstractApplicationEvent;
import myspring.springframework.context.ApplicationListener;
import myspring.springframework.util.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author Ryan
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    private final Set<ApplicationListener<AbstractApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    private BeanFactory beanFactory;

    /**
     * 设置工厂
     * @param beanFactory 工厂
     * @throws BeansException 异常
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 添加监听器
     *
     * @param listener 监听器
     */
    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<AbstractApplicationEvent>) listener);
    }

    /**
     * 移除监听器
     *
     * @param listener 监听器
     */
    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    /**
     * Return a Collection of ApplicationListeners matching the given
     * event type. Non-matching listeners get excluded early.
     * @param event the event to be propagated. Allows for excluding
     * non-matching listeners early, based on cached matching information.
     * @return a Collection of ApplicationListeners
     */
    protected Collection<ApplicationListener> getApplicationListeners(AbstractApplicationEvent event) {
        LinkedList<ApplicationListener> all = new LinkedList<>();
        for (ApplicationListener applicationListener : this.applicationListeners) {
            if (supportsEvent(applicationListener, event)){
                all.add(applicationListener);
            }
        }
        return all;
    }

    /**
     * 监听器是否对该事件感兴趣
     * @param applicationListener 监听器
     * @param event 事件
     * @return 布尔值
     */
    protected boolean supportsEvent(ApplicationListener<AbstractApplicationEvent> applicationListener, AbstractApplicationEvent event){
        Class<? extends ApplicationListener> aClass = applicationListener.getClass();
        // 根据策略模式的初始化实例化不同的类型，需要判断后获取目标class
        Class<?> targetClass = ClassUtils.isCglibProxyClass(aClass) ? aClass.getSuperclass() : aClass;
        Type genericInterface = targetClass.getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String clazz = actualTypeArgument.getTypeName();
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + clazz);
        }
        // 判定此 eventClassName 对象所表示的类或接口与指定的 event.getClass() 参数所表示的类或接口是否相同，或是否是其超类或超接口。
        // isAssignableFrom是用来判断子类和父类的关系的，或者接口的实现类和接口的关系的，默认所有的类的终极父类都是Object。如果A.isAssignableFrom(B)结果是true，证明B可以转换成为A,也就是A可以由B转换而来。
        return eventClassName.isAssignableFrom(event.getClass());
    }
}
