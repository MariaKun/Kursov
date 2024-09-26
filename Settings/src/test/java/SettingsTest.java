import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SettingsTest {

    private static Settings settings;

    @BeforeAll
    static void getSettings() throws IOException {
        settings = Settings.GetSettings();
        assertThat(settings).isNotNull();
    }

    @Test
    void getPort() {
        int port = settings.getPort();
        assertThat(port).isEqualTo(8080);
    }

    @Test
    void getHost() {
        String host = settings.getHost();
        assertThat(host).isEqualTo("localhost");
    }
}