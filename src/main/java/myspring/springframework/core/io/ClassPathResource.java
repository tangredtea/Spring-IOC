package myspring.springframework.core.io;

import cn.hutool.core.lang.Assert;
import myspring.springframework.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ryan
 */
public class ClassPathResource implements Resource{

    private final String path;

    private final ClassLoader classLoader;

    public ClassPathResource(String path){
        this(path, null);
    }

    public ClassPathResource(String path, ClassLoader classLoader){
        Assert.notNull(path, "Path must not be null");
        this.path = path;
        this.classLoader = (classLoader != null) ? classLoader : ClassUtils.getDefaultClassLoader();
    }

    /**
     * Get the input stream for this resource
     *
     * @return InputStream
     */
    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = classLoader.getResourceAsStream(path);
        if (is == null) {
            throw new FileNotFoundException(this.path + " cannot be opened because it does not exist");
        }
        return is;
    }
}
