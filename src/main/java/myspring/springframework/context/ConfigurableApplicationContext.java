package myspring.springframework.context;

/**
 * @author Ryan
 */
public interface ConfigurableApplicationContext extends ApplicationContext{
    /**
     * 刷新容器
     */
    void refresh();
}
