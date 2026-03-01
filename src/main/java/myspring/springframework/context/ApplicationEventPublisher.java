package myspring.springframework.context;

/**
 * @author Ryan
 */
public interface ApplicationEventPublisher {

    /**
     * Publish an event
     * @param event the event
     */
    void publishEvent(AbstractApplicationEvent event);

}
