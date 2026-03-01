package myspring.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Interface for a resource descriptor that abstracts from the actual type of underlying resource.
 *
 * @author Ryan
 */
public interface Resource {

    /**
     * Return an InputStream for the content of an underlying resource.
     *
     * @return the input stream for the underlying resource
     * @throws IOException if the stream could not be opened
     */
    InputStream getInputStream() throws IOException;

}
