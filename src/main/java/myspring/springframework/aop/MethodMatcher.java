package myspring.springframework.aop;

import java.lang.reflect.Method;

/**
 * 检查目标方法是否有资格获得通知。
 * @author Ryan
 */
public interface MethodMatcher {
    /**
     * 执行静态检查给定方法是否匹配。
     * @param method 方法
     * @param targetClass 目标class
     * @return 布尔值
     */
    boolean matches(Method method, Class<?> targetClass);
}
