package myspring.springframework.aop;

/**
 * Core Spring pointcut abstraction.
 *
 * @author Ryan
 */
public interface Pointcut {

    /**
     * Return the ClassFilter for this pointcut.
     *
     * @return the ClassFilter
     */
    ClassFilter getClassFilter();

    /**
     * Return the MethodMatcher for this pointcut.
     *
     * @return the MethodMatcher
     */
    MethodMatcher getMethodMatcher();
}
