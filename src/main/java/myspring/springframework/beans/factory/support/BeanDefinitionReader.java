package myspring.springframework.beans.factory.support;

import myspring.springframework.beans.factory.BeansException;
import myspring.springframework.core.io.Resource;
import myspring.springframework.core.io.ResourceLoader;

import java.io.IOException;

/**
 * @author Ryan
 */
public interface BeanDefinitionReader {

    /**
     * 得到注册表
     * @return beanDefinitionRegistry
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 得到资源加载器
     * @return resourceLoader
     */
    ResourceLoader getResourceLoader();

    /**
     * 加载资源
     * @param resource 资源
     * @throws BeansException 异常
     * @throws IOException 异常
     */
    void loadBeanDefinitions(Resource resource) throws BeansException, IOException;

    /**
     * 加载资源
     * @param resources 资源
     * @throws BeansException 异常
     * @throws IOException 异常
     */
    void loadBeanDefinitions(Resource... resources) throws BeansException, IOException;

    /**
     * 从指定路径加载
     * @param location 路径
     * @throws BeansException 异常
     */
    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String... locations) throws BeansException;

}