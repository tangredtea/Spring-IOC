package myspring.springframework.context.support;

import myspring.springframework.beans.BeansException;

/**
 * @author Ryan
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private String[] configLocations;

    public ClassPathXmlApplicationContext() {
    }

    /**
     * Load BeanDefinitions from XML and refresh the context
     *
     * @param configLocations the configuration file paths
     * @throws BeansException exception
     */
    public ClassPathXmlApplicationContext(String configLocations) throws BeansException {
        this(new String[]{configLocations});
    }

    /**
     * Load BeanDefinitions from XML and refresh the context
     * @param configLocations the configuration file paths
     * @throws BeansException exception
     */
    public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }

}
