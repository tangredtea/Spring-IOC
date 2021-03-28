package myspring.springframework.context;

/**
 * 应用程序上下文接口
 *
 */
public interface ApplicationContext {

    Object getBean(Class clazz) throws Exception;

    Object getBean(String beanName) throws Exception;

}
