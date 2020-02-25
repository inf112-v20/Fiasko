package inf112.fiasko.roborally.utility;

import java.io.InputStream;

public final class ResourceUtil {
    private ResourceUtil() {}

    /**
     * Gets an input stream for a given resource
     * @param resourcePath The relative path from the resources folder to the resource
     * @return An input stream
     */
    public static InputStream getResourceAsInputStream(String resourcePath) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream resourceStream = classloader.getResourceAsStream(resourcePath);
        if (resourceStream == null) {
            throw new IllegalArgumentException("Unable to load resource.");
        }
        return resourceStream;
    }
}
