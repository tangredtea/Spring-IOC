package myspring.springframework.beans.factory.config;

import myspring.springframework.beans.factory.HierarchicalBeanFactory;
import myspring.springframework.core.convert.ConversionService;
import myspring.springframework.util.StringValueResolver;
import org.jetbrains.annotations.Nullable;

/**
 * Configuration interface to be implemented by most bean factories.
 *
 * @author Ryan
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * Add a BeanPostProcessor that will get applied to beans created by this factory.
     *
     * @param beanPostProcessor the post-processor to register
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * Destroy all singleton beans in this factory.
     */
    void destroySingletons();

    /**
     * Add a String resolver for embedded values such as annotation attributes.
     *
     * @param valueResolver the String resolver to apply to embedded values
     */
    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    /**
     * Resolve the given embedded value, e.g. an annotation attribute.
     *
     * @param value the value to resolve
     * @return the resolved value (may be the original value as-is)
     */
    String resolveEmbeddedValue(String value);

    /**
     * Set the type conversion service to use for converting property values.
     *
     * @param conversionService the ConversionService to use
     */
    void setConversionService(ConversionService conversionService);

    /**
     * Return the associated ConversionService, if any.
     *
     * @return the ConversionService, or {@code null} if none
     */
    @Nullable
    ConversionService getConversionService();
}
