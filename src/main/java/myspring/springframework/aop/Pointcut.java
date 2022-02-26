package myspring.springframework.aop;

/**
 * 核心 Spring 切入点抽象
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
