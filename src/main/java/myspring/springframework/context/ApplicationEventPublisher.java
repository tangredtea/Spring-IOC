package myspring.springframework.context;

/**
 * @author Ryan
 */
public interface ApplicationEventPublisher {

    /**
     * 发布事件
     * @param event 事件
     */
    void publishEvent(AbstractApplicationEvent event);

}
