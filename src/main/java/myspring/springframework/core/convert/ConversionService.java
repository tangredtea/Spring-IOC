package myspring.springframework.core.convert;

import org.jetbrains.annotations.Nullable;

/**
 * A service interface for type conversion.
 *
 * @author Ryan
 */
public interface ConversionService {

    /**
     * Return whether objects of sourceType can be converted to the targetType.
     *
     * @param sourceType the source type to convert from
     * @param targetType the target type to convert to
     * @return true if a conversion can be performed
     */
    boolean canConvert(@Nullable Class<?> sourceType, Class<?> targetType);

    /**
     * Convert the given source to the specified targetType.
     *
     * @param source     the source object to convert
     * @param targetType the target type to convert to
     * @param <T>        the target type
     * @return the converted object
     */
    <T> T convert(Object source, Class<T> targetType);

}
