package myspring.springframework.context;

import java.util.EventObject;

/**
 * @author Ryan
 */
public abstract class AbstractApplicationEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public AbstractApplicationEvent(Object source) {
        super(source);
    }

}
