package myspring.springframework.util;

/**
 * @author Ryan
 */
public interface StringValueResolver {

    /**
     * Resolve a string value
     * @param strVal the string to resolve
     * @return value
     */
    String resolveStringValue(String strVal);
}
