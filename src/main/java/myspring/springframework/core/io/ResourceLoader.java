package myspring.springframework.core.io;

/**
 * @author Ryan
 */
public interface ResourceLoader {

    String CLASS_URL_PREFIX = "classpath:";

    /**
     * Get a Resource from the given location path
     * @param location the resource location
     * @return resource
     */
    Resource getResource(String location);
}
