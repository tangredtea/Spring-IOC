package myspring.springframework.core.io;

/**
 * @author Ryan
 */
public interface ResourceLoader {

    String CLASS_URL_PREFIX = "classpath:";

    /**
     * 从路径中get Resource
     * @param location 路径
     * @return resource
     */
    Resource getResource(String location);
}
