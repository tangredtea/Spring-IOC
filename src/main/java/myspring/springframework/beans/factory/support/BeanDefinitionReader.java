package myspring.springframework.beans.factory.support;

import myspring.springframework.beans.BeansException;
import myspring.springframework.core.io.Resource;
import myspring.springframework.core.io.ResourceLoader;

import java.io.IOException;

/**
 * @author Ryan
 */
public interface BeanDefinitionReader {

    /**
     * Get the bean definition registry
     * @return beanDefinitionRegistry
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * Get the resource loader
     * @return resourceLoader
     */
    ResourceLoader getResourceLoader();

    /**
     * Load bean definitions from the given resource
     * @param resource the resource to load from
     * @throws BeansException if an error occurs while loading bean definitions
     * @throws IOException if an I/O error occurs
     */
    void loadBeanDefinitions(Resource resource) throws BeansException, IOException;

    /**
     * Load bean definitions from multiple resources
     * @param resources the resources to load from
     * @throws BeansException if an error occurs while loading bean definitions
     * @throws IOException if an I/O error occurs
     */
    void loadBeanDefinitions(Resource... resources) throws BeansException, IOException;

    /**
     * Load bean definitions from the specified location
     * @param location the resource location
     * @throws BeansException if an error occurs while loading bean definitions
     */
    void loadBeanDefinitions(String location) throws BeansException;

    /**
     * Load bean definitions from multiple locations
     * @param locations the resource locations
     * @throws BeansException if an error occurs while loading bean definitions
     */
    void loadBeanDefinitions(String... locations) throws BeansException;

}