package myspring.springframework.context;

import myspring.springframework.beans.factory.Aware;
import myspring.springframework.beans.factory.BeansException;

/**
 * @author Ryan
 */
public interface ApplicationContextAware extends Aware {

    /**
     * 设置应用上下文
     * @param applicationContext 应用上下文
     * @throws BeansException 异常
     */
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

}
