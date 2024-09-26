import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class Settings {
    private int port;
    private String host;

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public static Settings GetSettings() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("settings.json");
        return objectMapper.readValue(is, Settings.class);
    }
}
