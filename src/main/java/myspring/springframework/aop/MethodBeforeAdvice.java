package myspring.springframework.aop;


import org.aopalliance.aop.Advice;

import java.lang.reflect.Method;

/**
 * Advice invoked before a method is invoked.
 *
 * @author Ryan
 */
public interface MethodBeforeAdvice extends Advice {

    /**
     * Callback before a given method is invoked.
     *
     * @param method the method being invoked
     * @param args   the arguments to the method
     * @param target the target of the method invocation
     * @throws Throwable if this object wishes to abort the call
     */
    void before(Method method, Object[] args, Object target) throws Throwable;
}
