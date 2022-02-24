package myspring.springframework.aop;

/**
 * @author Ryan
 */
public interface Pointcut {

    /**
     * 得到class过滤器
     * @return filter
     */
    ClassFilter getClassFilter();

    /**
     * 方法匹配
     * @return matcher
     */
    MethodMatcher getMethodMatcher();
}
