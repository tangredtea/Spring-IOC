package myspring.springframework.beans.factory.support;

import myspring.springframework.core.io.DefaultResourceLoader;
import myspring.springframework.core.io.ResourceLoader;

/**
 * @author Ryan
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{

    private final BeanDefinitionRegistry registry;

    private final ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry){
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader){
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    /**
     * Get the bean definition registry
     *
     * @return beanDefinitionRegistry
     */
    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    /**
     * Get the resource loader
     *
     * @return resourceLoader
     */
    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
