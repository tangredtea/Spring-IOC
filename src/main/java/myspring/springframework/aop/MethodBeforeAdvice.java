package myspring.springframework.aop;


import org.aopalliance.aop.Advice;

import java.lang.reflect.Method;

/**
 * @author Ryan
 */
public interface MethodBeforeAdvice extends Advice {

    /**
     * 方法之前进行拦截
     * @param method 方法
     * @param args 参数
     * @param target 目标对象
     * @throws Throwable 异常
     */
    void before(Method method, Object[] args, Object target) throws Throwable;
}
