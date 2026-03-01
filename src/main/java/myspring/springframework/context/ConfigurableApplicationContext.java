package myspring.springframework.context;

/**
 * @author Ryan
 */
public interface ConfigurableApplicationContext extends ApplicationContext{
    /**
     * Refresh the container
     */
    void refresh();

    /**
     * Register a shutdown hook
     */
    void registerShutdownHook();

    /**
     * Manually close the application context
     */
    void close();
}
