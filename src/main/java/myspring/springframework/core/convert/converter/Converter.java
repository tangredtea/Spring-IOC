package myspring.springframework.core.convert.converter;

/**
 * A converter converts a source object of type S to a target of type T.
 *
 * @author Ryan
 */
public interface Converter<S, T> {

    /**
     * Convert the source object of type S to target type T.
     *
     * @param source the source object to convert
     * @return the converted object
     */
    T convert(S source);
}
