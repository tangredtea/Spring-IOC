package myspring.springframework.beans.factory.support;

import myspring.springframework.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import java.lang.reflect.Constructor;

/**
 * CGLIB-based instantiation strategy using subclass generation.
 *
 * @author Ryan
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {
    /**
     * Instantiate a bean using CGLIB subclass generation.
     *
     * @param beanDefinition the bean definition
     * @param beanName       the name of the bean
     * @param ctor           the constructor to use
     * @param args           the constructor arguments
     * @return the instantiated object
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<?> ctor, Object[] args) {
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
