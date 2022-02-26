package myspring.springframework.aop.framework;

/**
 *  AOP 代理的抽象
 * @author Ryan
 */
public interface AopProxy {

    /**
     * 得到代理类
     * @return object
     */
    Object getProxy();

}
