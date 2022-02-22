package myspring.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ryan
 */
public interface Resource {

    /**
     * 得到输入流
     * @return InputStream
     * @throws IOException 异常
     */
    InputStream getInputStream() throws IOException;

}
