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
     * Set the bean factory
     * @param beanFactory the bean factory
     * @throws BeansException exception
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * Add a listener
     *
     * @param listener the listener
     */
    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<AbstractApplicationEvent>) listener);
    }

    /**
     * Remove a listener
     *
     * @param listener the listener
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
     * Check whether the listener is interested in the given event
     * @param applicationListener the listener
     * @param event the event
     * @return boolean
     */
    protected boolean supportsEvent(ApplicationListener<AbstractApplicationEvent> applicationListener, AbstractApplicationEvent event){
        Class<? extends ApplicationListener> aClass = applicationListener.getClass();
        // Different types are instantiated based on the strategy pattern; determine and retrieve the target class accordingly
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
        // Determine whether the class or interface represented by eventClassName is the same as, or is a superclass/superinterface of,
        // the class or interface represented by event.getClass().
        // isAssignableFrom checks the relationship between a subclass and a superclass, or between an implementation and its interface.
        // All classes ultimately extend Object. If A.isAssignableFrom(B) returns true, it means B can be cast to A.
        return eventClassName.isAssignableFrom(event.getClass());
    }
}
