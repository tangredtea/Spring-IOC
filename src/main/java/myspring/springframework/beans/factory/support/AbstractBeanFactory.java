package myspring.springframework.beans.factory.support;

import myspring.springframework.beans.BeansException;
import myspring.springframework.beans.factory.FactoryBean;
import myspring.springframework.beans.factory.config.BeanDefinition;
import myspring.springframework.beans.factory.config.BeanPostProcessor;
import myspring.springframework.beans.factory.config.ConfigurableBeanFactory;
import myspring.springframework.util.ClassUtils;
import myspring.springframework.util.StringValueResolver;

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

    /**
     * 获取bean
     *
     * @param name bean名字
     * @return object
     * @throws BeansException 异常
     */
    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    /**
     * 获取bean
     *
     * @param name 名字
     * @param args 参数
     * @return object
     * @throws BeansException 异常
     */
    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    /**
     * 获取对象
     *
     * @param name         对象名
     * @param requiredType 需要的class
     * @return bean
     * @throws BeansException 异常
     */
    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    /**
     * 得到单例对象
     *
     * @param beanName 单例对象名
     * @return object
     */
    @Override
    public Object getSingleton(String beanName) {
        return super.getSingleton(beanName);
    }

    protected <T> T doGetBean(final String name, final Object[] args){
        Object bean = getSingleton(name);
        if (bean != null){
            // 如果是FactoryBean，则需要调用FactoryBean
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
     * 得到bean定义
     * @param beanName 名字
     * @return beanDefinition
     * @throws BeansException 异常
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 创造Bean
     * @param beanName 名字
     * @param beanDefinition 定义
     * @param args 参数
     * @return object
     * @throws BeansException 异常
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
}
