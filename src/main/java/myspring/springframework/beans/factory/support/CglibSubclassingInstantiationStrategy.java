package myspring.springframework.beans.factory.support;

import myspring.springframework.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import java.lang.reflect.Constructor;

/**
 * @author Ryan
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy{
    /**
     * 实例化策略
     *
     * @param beanDefinition 定义
     * @param beanName       名字
     * @param ctor           构造器
     * @param args           参数
     * @return object
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        if ( null == ctor){
            return enhancer.create();
        }
        return enhancer.create(ctor.getParameterTypes(), args);
    }
}
