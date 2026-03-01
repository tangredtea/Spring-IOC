package myspring.springframework.context;

import myspring.springframework.beans.factory.Aware;
import myspring.springframework.beans.BeansException;

/**
 * @author Ryan
 */
public interface ApplicationContextAware extends Aware {

    /**
     * Set the application context
     * @param applicationContext the application context
     * @throws BeansException exception
     */
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

}
