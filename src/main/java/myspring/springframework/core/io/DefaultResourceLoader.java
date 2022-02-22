package myspring.springframework.core.io;

import cn.hutool.core.io.resource.UrlResource;
import cn.hutool.core.lang.Assert;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Ryan
 */
public class DefaultResourceLoader implements ResourceLoader{
    /**
     * 从路径中get Resource
     *
     * @param location 路径
     * @return resource
     */
    @Override
    public Resource getResource(String location) {
        Assert.notNull(location, "Location must not be null !");
        if (location.startsWith(CLASS_URL_PREFIX)){
            return new ClassPathResource(location.substring(CLASS_URL_PREFIX.length()));
        }else {
            try{
                URL url = new URL(location);
                return (Resource) new UrlResource(url);
            }catch (MalformedURLException e){
                return new FileSystemResource(location);
            }
        }
    }
}
