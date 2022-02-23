package myspring.springframework.context;

/**
 * @author Ryan
 */
public interface ConfigurableApplicationContext extends ApplicationContext{
    /**
     * 刷新容器
     */
    void refresh();

    /**
     * 注册关闭监听
     */
    void registerShutdownHook();

    /**
     * 手动关闭逻辑
     */
    void close();
}
