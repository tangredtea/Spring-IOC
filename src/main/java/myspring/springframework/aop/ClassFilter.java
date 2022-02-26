package myspring.springframework.aop;

/**
 * 限制切入点匹配或对给定目标类的匹配的过滤器。
 * @author Ryan
 */
public interface ClassFilter {

    /**
     * 匹配该类或者接口是否适用
     * @param clazz 类
     * @return 布尔值
     */
    boolean matches(Class<?> clazz);


}
