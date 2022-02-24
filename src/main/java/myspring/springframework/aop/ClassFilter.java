package myspring.springframework.aop;

/**
 * @author Ryan
 */
public interface ClassFilter {

    /**
     * 匹配方法
     * @param clazz 类
     * @return 布尔值
     */
    boolean matches(Class<?> clazz);


}
