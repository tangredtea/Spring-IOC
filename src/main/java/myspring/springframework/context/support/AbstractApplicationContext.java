package myspring.springframework.context.support;

import myspring.springframework.beans.BeansException;
import myspring.springframework.beans.factory.ConfigurableListableBeanFactory;
import myspring.springframework.beans.factory.config.BeanFactoryPostProcessor;
import myspring.springframework.beans.factory.config.BeanPostProcessor;
import myspring.springframework.context.AbstractApplicationEvent;
import myspring.springframework.context.ApplicationListener;
import myspring.springframework.context.ConfigurableApplicationContext;
import myspring.springframework.context.event.ApplicationEventMulticaster;
import myspring.springframework.context.event.ContextClosedEvent;
import myspring.springframework.context.event.ContextRefreshedEvent;
import myspring.springframework.context.event.SimpleApplicationEventMulticaster;
import myspring.springframework.core.convert.ConversionService;
import myspring.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * @author Ryan
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException {
        // 1. Create the BeanFactory and load BeanDefinitions
        refreshBeanFactory();

        // 2. Get the BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3. Add ApplicationContextAwareProcessor so that beans implementing ApplicationContextAware can access the ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 4. Execute BeanFactoryPostProcessors before bean instantiation (invoke factory processors registered as beans in the context)
        invokeBeanFactoryPostProcessors(beanFactory);

        // 5. Register BeanPostProcessors before other beans are instantiated
        registerBeanPostProcessors(beanFactory);

        // 6. Initialize the event multicaster
        initApplicationEventMulticaster();

        // 7. Register event listeners
        registerListeners();

        // 8. Pre-instantiate singleton bean objects
        finishBeanFactoryInitialization(beanFactory);
        // 9. Publish the container refresh completed event
        finishRefresh();
    }

    /**
     * Set the type conversion service
     * @param beanFactory the bean factory
     */
    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
        // Set the type conversion service
        if (beanFactory.containsBean("conversionService")) {
            Object conversionService = beanFactory.getBean("conversionService");
            if (conversionService instanceof ConversionService) {
                beanFactory.setConversionService((ConversionService) conversionService);
            }
        }

        // Pre-instantiate singleton bean objects
        beanFactory.preInstantiateSingletons();
    }

    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(AbstractApplicationEvent event){
        applicationEventMulticaster.multicastEvent(event);
    }

    private void registerListeners() {
        Collection<ApplicationListener> values = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener value : values) {
            applicationEventMulticaster.addApplicationListener(value);
        }
    }

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    /**
     * Register a shutdown hook
     */
    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }

    @Override
    public boolean containsBean(String name) {
        return getBeanFactory().containsBean(name);
    }

    /**
     * Manually close the application context
     */
    @Override
    public void close() {
        // Publish the container closed event
        publishEvent(new ContextClosedEvent(this));

        // Execute destroy methods on singleton beans
        getBeanFactory().destroySingletons();
    }

    /**
     * Refresh the BeanFactory
     * @throws BeansException exception
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    /**
     * Get the BeanFactory
     * @return beanFactory
     */
    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }



    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }
}
