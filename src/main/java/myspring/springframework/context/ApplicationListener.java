package myspring.springframework.context;

import java.util.EventListener;

/**
 * @author Ryan
 */
public interface ApplicationListener<E extends AbstractApplicationEvent> extends EventListener {

    /**
     * Handle an application event.
     * @param event the event to respond to
     */
    void onApplicationEvent(E event);

}
