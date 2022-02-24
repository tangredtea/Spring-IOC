package myspring.springframework.context.event;

import myspring.springframework.context.AbstractApplicationEvent;
import myspring.springframework.context.ApplicationListener;

/**
 * @author Ryan
 */
public interface ApplicationEventMulticaster {

    /**
     * 添加监听器
     * @param listener 监听器
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 移除监听器
     * @param listener 监听器
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 广播事件
     * @param event 事件
     */
    void multicastEvent(AbstractApplicationEvent event);
}
