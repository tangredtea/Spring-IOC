package myspring.springframework.util;

/**
 * @author Ryan
 */
public interface StringValueResolver {

    /**
     * 解析字符串接口
     * @param strVal 字符串
     * @return value
     */
    String resolveStringValue(String strVal);
}
