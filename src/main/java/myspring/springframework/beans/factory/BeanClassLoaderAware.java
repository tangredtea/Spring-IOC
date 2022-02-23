package myspring.springframework.beans.factory;

/**
 * @author Ryan
 */
public interface BeanClassLoaderAware {

    /**
     * 设置类加载器
     * @param classLoader 类加载器
     */
    void setBeanClassLoader(ClassLoader classLoader);
}
