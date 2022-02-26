package myspring.springframework.beans.factory.support;

import myspring.springframework.beans.BeansException;
import myspring.springframework.beans.factory.ConfigurableListableBeanFactory;
import myspring.springframework.beans.factory.config.BeanDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ryan
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    /**
     * 得到bean定义
     *
     * @param beanName 名字
     * @return beanDefinition
     * @throws BeansException 异常
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null){
            throw  new BeansException("No bean named " + beanName + " is defined");
        }
        return beanDefinition;
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> result = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class beanClass = beanDefinition.getBeanClass();
            if (type.isAssignableFrom(beanClass)) {
                result.put(beanName, (T) getBean(beanName));
            }
        });
        return result;
    }

    /**
     * 判断是否包含指定名称的BeanDefinition
     *
     * @param beanName 名字
     * @return 布尔值
     */
    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    /**
     * 返回注册表中的所有names
     *
     * @return name
     */
    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    /**
     * 向注册表中注册 BeanDefinition
     *
     * @param beanName       名字
     * @param beanDefinition 定义
     */
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {
        beanDefinitionMap.keySet().forEach(this::getBean);
    }

    /**
     * 由class得到bean
     *
     * @param requiredType class
     * @return bean
     * @throws BeansException 异常
     */
    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        List<String> beanNames = new ArrayList<>();
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            Class beanClass = entry.getValue().getBeanClass();
            if (requiredType.isAssignableFrom(beanClass)) {
                beanNames.add(entry.getKey());
            }
        }
        if (1 == beanNames.size()) {
            return getBean(beanNames.get(0), requiredType);
        }

        throw new BeansException(requiredType + "expected single bean but found " + beanNames.size() + ": " + beanNames);
    }

    /**
     * 判断是否包含bean
     *
     * @param name bean名字
     * @return 布尔值
     */
    @Override
    public boolean containsBean(String name) {
        return beanDefinitionMap.containsKey(name);
    }
}
