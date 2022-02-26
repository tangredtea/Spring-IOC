package myspring.springframework.context.support;

import myspring.springframework.beans.factory.FactoryBean;
import myspring.springframework.beans.factory.InitializingBean;
import myspring.springframework.core.convert.ConversionService;
import myspring.springframework.core.convert.converter.Converter;
import myspring.springframework.core.convert.converter.ConverterFactory;
import myspring.springframework.core.convert.converter.ConverterRegistry;
import myspring.springframework.core.convert.converter.GenericConverter;
import myspring.springframework.core.convert.support.DefaultConversionService;
import myspring.springframework.core.convert.support.GenericConversionServiceImpl;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * @author Ryan
 */
public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {

    @Nullable
    private Set<?> converters;

    @Nullable
    private GenericConversionServiceImpl conversionService;

    @Override
    public ConversionService getObject() throws Exception {
        return conversionService;
    }

    @Override
    public Class<?> getObjectType() {
        return conversionService.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() {
        this.conversionService = new DefaultConversionService();
        registerConverters(converters, conversionService);
    }

    private void registerConverters(Set<?> converters, ConverterRegistry registry) {
        if (converters != null) {
            for (Object converter : converters) {
                if (converter instanceof GenericConverter) {
                    registry.addConverter((GenericConverter) converter);
                } else if (converter instanceof Converter<?, ?>) {
                    registry.addConverter((Converter<?, ?>) converter);
                } else if (converter instanceof ConverterFactory<?, ?>) {
                    registry.addConverterFactory((ConverterFactory<?, ?>) converter);
                } else {
                    throw new IllegalArgumentException("Each converter object must implement one of the " +
                            "Converter, ConverterFactory, or GenericConverter interfaces");
                }
            }
        }
    }

    public void setConverters(Set<?> converters) {
        this.converters = converters;
    }

}
