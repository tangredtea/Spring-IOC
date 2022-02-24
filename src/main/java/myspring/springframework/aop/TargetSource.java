package myspring.springframework.aop;

/**
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
        return this.target.getClass().getInterfaces();
    }
}
