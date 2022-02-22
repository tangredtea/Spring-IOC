package myspring.springframework.core.io;

import cn.hutool.core.lang.Assert;
import myspring.springframework.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ryan
 */
public class ClassPathResource implements Resource{

    private final String path;

    private ClassLoader classLoader;

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
     * @throws IOException 异常
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return classLoader.getResourceAsStream(path);
    }
}
