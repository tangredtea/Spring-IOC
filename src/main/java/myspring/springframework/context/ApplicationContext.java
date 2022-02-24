package myspring.springframework.context;

import myspring.springframework.beans.factory.HierarchicalBeanFactory;
import myspring.springframework.beans.factory.ListableBeanFactory;
import myspring.springframework.core.io.ResourceLoader;

/**
 * @author Ryan
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {

}
