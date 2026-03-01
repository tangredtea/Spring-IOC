package myspring.springframework.beans.factory;

import myspring.springframework.beans.BeansException;
import myspring.springframework.beans.PropertyValue;
import myspring.springframework.beans.PropertyValues;
import myspring.springframework.beans.factory.config.BeanDefinition;
import myspring.springframework.beans.factory.config.BeanFactoryPostProcessor;
import myspring.springframework.core.io.DefaultResourceLoader;
import myspring.springframework.core.io.Resource;
import myspring.springframework.util.StringValueResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Ryan
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String location;


    /**
     * After all BeanDefinitions are loaded and before bean objects are instantiated,
     * provide a mechanism to modify BeanDefinition properties
     *
     * @param beanFactory the bean factory
     * @throws BeansException exception
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            // Load the properties file
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);

            // Replace placeholder values with property values
            Properties properties = new Properties();
            try (InputStream inputStream = resource.getInputStream()) {
                properties.load(inputStream);
            }

            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
            for (String beanName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value = propertyValue.getValue();
                    if (!(value instanceof String)) {
                        continue;
                    }
                    value = resolvePlaceholder((String) value, properties);
                    propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), value));
                }
            }

            // Add a string value resolver to the container for resolving @Value annotations
            StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
            beanFactory.addEmbeddedValueResolver(valueResolver);

        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    private String resolvePlaceholder(String value, Properties properties) {
        String strVal = value;
        StringBuilder buffer = new StringBuilder(strVal);
        int startIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        int stopIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
        if (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx) {
            String propKey = strVal.substring(startIdx + 2, stopIdx);
            String propVal = properties.getProperty(propKey);
            buffer.replace(startIdx, stopIdx + 1, propVal);
        }
        return buffer.toString();
    }

    public void setLocation(String location){
        this.location = location;
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver{

        private final Properties properties;

        private PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        /**
         * Resolve a string value
         *
         * @param strVal the string to resolve
         * @return value
         */
        @Override
        public String resolveStringValue(String strVal) {
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(strVal, properties);
        }
    }
}
