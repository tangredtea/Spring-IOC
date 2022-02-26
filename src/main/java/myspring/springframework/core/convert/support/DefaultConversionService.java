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
        // 添加各类类型转换工厂
        converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
    }

}
