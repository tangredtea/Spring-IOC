package myspring.springframework.context.event;

import myspring.springframework.context.AbstractApplicationEvent;
import myspring.springframework.context.ApplicationListener;

/**
 * @author Ryan
 */
public interface ApplicationEventMulticaster {

    /**
     * Add a listener
     * @param listener the listener
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * Remove a listener
     * @param listener the listener
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * Multicast the event
     * @param event the event
     */
    void multicastEvent(AbstractApplicationEvent event);
}
