package myspring.springframework.beans.factory.config;

import myspring.springframework.beans.factory.HierarchicalBeanFactory;
import myspring.springframework.core.convert.ConversionService;
import myspring.springframework.util.StringValueResolver;
import org.jetbrains.annotations.Nullable;

/**
 * @author Ryan
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 添加对象处理
     * @param beanPostProcessor 对象处理器
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁对象
     */
    void destroySingletons();

    /**
     * Add a String resolver for embedded values such as annotation attributes.
     * @param valueResolver the String resolver to apply to embedded values
     * @since 3.0
     */
    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    /**
     * Resolve the given embedded value, e.g. an annotation attribute.
     * @param value the value to resolve
     * @return the resolved value (may be the original value as-is)
     * @since 3.0
     */
    String resolveEmbeddedValue(String value);


    /**
     * 设置类型转换服务
     * @param conversionService service
     */
    void setConversionService(ConversionService conversionService);

    /**
     * 返回类型转换服务
     * @return conversionService
     */
    @Nullable
    ConversionService getConversionService();
}
