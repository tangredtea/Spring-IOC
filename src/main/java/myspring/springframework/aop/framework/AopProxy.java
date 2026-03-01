package myspring.springframework.aop.framework;

/**
 * Delegate interface for creating AOP proxies.
 *
 * @author Ryan
 */
public interface AopProxy {

    /**
     * Create a new proxy object.
     *
     * @return the proxy object
     */
    Object getProxy();

}
