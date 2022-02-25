package myspring.springframework.beans.factory;

import myspring.springframework.beans.factory.config.BeanDefinition;
import myspring.springframework.beans.factory.config.BeanFactoryPostProcessor;
import myspring.springframework.core.io.DefaultResourceLoader;
import myspring.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String location;


    /**
     * 在所有的 BeanDefinition 加载完成后，实例化 Bean 对象之前，提供修改 BeanDefinition 属性的机制
     *
     * @param beanFactory 容器
     * @throws BeansException 异常
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try{
            DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
            Resource resource = defaultResourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
            for (String beanDefinitionName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value = propertyValue.getValue();
                    if (!(value instanceof String)){
                        continue;
                    }
                    String strVal = (String) value;
                    StringBuilder stringBuilder = new StringBuilder(strVal);
                    int start = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
                    int stop = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
                    if (start != -1 && stop != -1 && start < stop){
                        String propKey = strVal.substring(start + 2, stop);
                        String propVal = properties.getProperty(propKey);
                        stringBuilder.replace(start, stop + 1, propVal);
                        propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), stringBuilder.toString()));
                    }
                }
            }
        }catch (IOException e){
            throw new BeansException("Could not load properties", e);
        }
    }

    public void setLocation(String location){
        this.location = location;
    }

}
