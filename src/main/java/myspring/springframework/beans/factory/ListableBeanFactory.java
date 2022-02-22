package myspring.springframework.beans.factory;

import java.util.Map;

/**
 * @author Ryan
 */
public interface ListableBeanFactory extends BeanFactory{

    /**
     * 按照类型返回 Bean 实例
     * @param type 类型
     * @param <T> 泛型
     * @return map
     * @throws BeansException 异常
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 返回注册表中的所有name
     * @return name数组
     */
    String[] getBeanDefinitionNames();

}