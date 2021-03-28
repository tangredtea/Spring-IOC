package myspring.springframework.reader;

import myspring.springframework.entity.BeanDefinition;
import myspring.springframework.io.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * BeanDefinitionReader实现的抽象类
 *
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private Map<String, BeanDefinition> registry;

    private ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(ResourceLoader resourceLoader) {
        this.registry = new HashMap<>();
        this.resourceLoader = resourceLoader;
    }

    public Map<String, BeanDefinition> getRegistry() {
        return registry;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

}
