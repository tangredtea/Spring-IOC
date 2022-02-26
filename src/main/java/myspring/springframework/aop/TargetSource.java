package myspring.springframework.aop;

import myspring.springframework.util.ClassUtils;

/**
 * 用于获取 AOP 调用的当前“目标”，如果没有环绕通知选择结束拦截器链本身，则将通过反射调用该目标。
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
