package myspring.springframework.aop.framework;

import myspring.springframework.aop.AdvisedSupport;

/**
 * @author Ryan
 */
public class ProxyFactory {

    private AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport){
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy(){
        return createProxy().getProxy();
    }

    private AopProxy createProxy(){
        if (advisedSupport.isProxyTargetClass()){
            return new Cglib2AopProxy(advisedSupport);
        }
        return new JdkDynamicAopProxy(advisedSupport);
    }
}
