package myspring.springframework.context.event;

import myspring.springframework.context.AbstractApplicationEvent;
import myspring.springframework.context.ApplicationContext;

/**
 * @author Ryan
 */
public class ApplicationContextEvent extends AbstractApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext(){
        return (ApplicationContext) getSource();
    }
}
