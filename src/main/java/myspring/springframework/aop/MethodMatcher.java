package myspring.springframework.aop;

import java.lang.reflect.Method;

/**
 * @author Ryan
 */
public interface MethodMatcher {
    /**
     * 匹配方法
     * @param method 方法
     * @param targetClass 目标class
     * @return 布尔值
     */
    boolean matches(Method method, Class<?> targetClass);
}
