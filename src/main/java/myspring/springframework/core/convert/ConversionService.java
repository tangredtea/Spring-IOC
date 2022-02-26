package myspring.springframework.core.convert;

import org.jetbrains.annotations.Nullable;

/**
 * @author Ryan
 */
public interface ConversionService {

    /**
     * 判断是否能转换
     * @param sourceType 源
     * @param targetType 目标
     * @return 布尔值
     */
    boolean canConvert(@Nullable Class<?> sourceType, Class<?> targetType);

    /**
     * 转换类型
     * @param source 源
     * @param targetType 目标
     * @param <T> 泛型
     * @return object
     */
    <T> T convert(Object source, Class<T> targetType);

}
