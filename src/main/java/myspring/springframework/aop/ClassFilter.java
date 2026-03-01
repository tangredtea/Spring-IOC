package myspring.springframework.aop;

/**
 * Filter that restricts matching of a pointcut to a given set of target classes.
 *
 * @author Ryan
 */
public interface ClassFilter {

    /**
     * Should the pointcut apply to the given interface or target class?
     *
     * @param clazz the candidate target class
     * @return whether the advice should apply to the given target class
     */
    boolean matches(Class<?> clazz);


}
