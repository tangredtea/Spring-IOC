package myspring.springframework.beans.factory.support;

import myspring.springframework.beans.BeansException;
import myspring.springframework.beans.factory.FactoryBean;
import myspring.springframework.beans.factory.config.BeanDefinition;
import myspring.springframework.beans.factory.config.BeanPostProcessor;
import myspring.springframework.beans.factory.config.ConfigurableBeanFactory;
import myspring.springframework.core.convert.ConversionService;
import myspring.springframework.util.ClassUtils;
import myspring.springframework.util.StringValueResolver;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan
 */
public abstract class AbstractBeanFactory extends AbstractFactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    /** ClassLoader to resolve bean class names with, if necessary */
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    /** BeanPostProcessors to apply in createBean */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    private ConversionService conversionService;

    /**
     * Get a bean instance
     *
     * @param name bean name
     * @return object
     * @throws BeansException exception
     */
    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    /**
     * Get a bean instance
     *
     * @param name bean name
     * @param args constructor arguments
     * @return object
     * @throws BeansException exception
     */
    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    /**
     * Get a bean instance of the specified type
     *
     * @param name         bean name
     * @param requiredType required class type
     * @return bean
     * @throws BeansException exception
     */
    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    /**
     * Get a singleton bean instance
     *
     * @param beanName singleton bean name
     * @return object
     */
    @Override
    public Object getSingleton(String beanName) {
        return super.getSingleton(beanName);
    }

    protected <T> T doGetBean(final String name, final Object[] args){
        Object bean = getSingleton(name);
        if (bean != null){
            // If it is a FactoryBean, invoke the FactoryBean to get the object
            return (T) getObjectForBeanInstance(bean, name);
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object o = createBean(name, beanDefinition, args);
        return (T) getObjectForBeanInstance(o, name);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String beanName){
        if (!(beanInstance instanceof FactoryBean)){
            return beanInstance;
        }
        Object object = getCachedObjectForFactoryBean(beanName);
        if (object == null){
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }
        return object;
    }

    /**
     * Get the bean definition
     * @param beanName bean name
     * @return beanDefinition
     * @throws BeansException exception
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * Create a bean instance
     * @param beanName bean name
     * @param beanDefinition bean definition
     * @param args constructor arguments
     * @return object
     * @throws BeansException exception
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws  BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors(){
        return this.beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return beanClassLoader;
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver resolver : this.embeddedValueResolvers) {
            result = resolver.resolveStringValue(result);
        }
        return result;
    }

    @Nullable
    @Override
    public ConversionService getConversionService() {
        return conversionService;
    }

    @Override
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }
}
