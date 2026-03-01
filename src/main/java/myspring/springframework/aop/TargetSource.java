package myspring.springframework.aop;

import myspring.springframework.util.ClassUtils;

/**
 * Used to obtain the current “target” of an AOP invocation, which will be invoked via reflection
 * if no around advice chooses to end the interceptor chain itself.
 * @author Ryan
 */
public class TargetSource {

    private final Object target;

    public TargetSource(Object target){
        this.target = target;
    }

    public Object getTarget() {
        return this.target;
    }

    public Class<?>[] getTargetClass(){
        Class<?> aClass = this.target.getClass();
        aClass = ClassUtils.isCglibProxyClass(aClass) ? aClass.getSuperclass() : aClass;
        return aClass.getInterfaces();
    }

}
