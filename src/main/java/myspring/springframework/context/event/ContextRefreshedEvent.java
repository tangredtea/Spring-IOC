package myspring.springframework.context.event;

import myspring.springframework.context.AbstractApplicationEvent;

/**
 * @author Ryan
 */
public class ContextRefreshedEvent extends AbstractApplicationEvent {

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
