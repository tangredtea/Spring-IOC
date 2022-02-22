package myspring.springframework.core.io;

import cn.hutool.core.lang.Assert;
import myspring.springframework.util.ClassUtils;
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
     * 得到输入流
     *
     * @return InputStream
     */
    @Override
    public InputStream getInputStream() {
        return classLoader.getResourceAsStream(path);
    }
}
