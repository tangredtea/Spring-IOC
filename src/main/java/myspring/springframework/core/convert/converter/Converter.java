package myspring.springframework.core.convert.converter;

/**
 * @author Ryan
 */
public interface Converter<S, T> {

    /**
     * 转换
     * @param source 源
     * @return 转换后的类型
     */
    T convert(S source);
}
