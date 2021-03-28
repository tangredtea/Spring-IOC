package myspring.springframework.factory;

import myspring.springframework.entity.BeanDefinition;
import myspring.springframework.entity.BeanReference;
import myspring.springframework.entity.PropertyValue;

import java.lang.reflect.Field;

public class AutowiredCapableBeanFactory extends AbstractBeanFactory {

    @Override
    Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
        if(beanDefinition.isSingleton() && beanDefinition.getBean() != null) {
            return beanDefinition.getBean();
        }
        Object bean = beanDefinition.getBeanClass().newInstance();
        if(beanDefinition.isSingleton()) {
            beanDefinition.setBean(bean);
        }
        applyPropertyValues(bean, beanDefinition);
        return bean;
    }

    /**
     * 为新创建了bean注入属性
     * @param bean 待注入属性的bean
     * @param beanDefinition bean的定义
     * @throws Exception 反射异常
     */
    void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {
        for(PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {
            Field field = bean.getClass().getDeclaredField(propertyValue.getName());
            Object value = propertyValue.getValue();
            if(value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) propertyValue.getValue();
                // 优先按照自定义名称匹配
                BeanDefinition refDefinition = beanDefinitionMap.get(beanReference.getName());
                if(refDefinition != null) {
                    if(!refDefinition.isSingleton() || refDefinition.getBean() == null) {
                        value = doCreateBean(refDefinition);
                    } else {
                        value = refDefinition.getBean();
                    }
                } else {
                    // 按照类型匹配，返回第一个匹配的
                    Class clazz = Class.forName(beanReference.getName());
                    for(BeanDefinition definition : beanDefinitionMap.values()) {
                        if(clazz.isAssignableFrom(definition.getBeanClass())) {
                            if(!definition.isSingleton() || definition.getBean() == null) {
                                value = doCreateBean(definition);
                            } else {
                                value = definition.getBean();
                            }
                        }
                    }
                }

            }
            if(value == null) {
                throw new RuntimeException("无法注入");
            }
            field.setAccessible(true);
            field.set(bean, value);
        }
    }


}
