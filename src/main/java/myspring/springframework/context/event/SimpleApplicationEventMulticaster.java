package myspring.springframework.context.event;

import myspring.springframework.beans.factory.BeanFactory;
import myspring.springframework.context.AbstractApplicationEvent;
import myspring.springframework.context.ApplicationListener;

/**
 * @author Ryan
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory){
        setBeanFactory(beanFactory);
    }
    /**
     * Multicast the event
     *
     * @param event the event
     */
    @Override
    public void multicastEvent(AbstractApplicationEvent event) {
        for (ApplicationListener applicationListener : getApplicationListeners(event)) {
            applicationListener.onApplicationEvent(event);
        }
    }
}
