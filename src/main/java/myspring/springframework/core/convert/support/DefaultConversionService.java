package myspring.springframework.core.convert.support;

import myspring.springframework.core.convert.converter.ConverterRegistry;

/**
 * @author Ryan
 */
public class DefaultConversionService extends GenericConversionServiceImpl {

    public DefaultConversionService() {
        addDefaultConverters(this);
    }

    public static void addDefaultConverters(ConverterRegistry converterRegistry) {
        // Add default type converter factories
        converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
    }

}
