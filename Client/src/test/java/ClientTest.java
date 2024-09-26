import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class ClientTest {
    @Test
    void setName_success() {
        String name = "testName";
        System.setIn(new ByteArrayInputStream(name.getBytes()));
        Scanner scanner = new Scanner(System.in);
        StringWriter output = new StringWriter();
        PrintWriter out = new PrintWriter(output);
        Client client = new Client(new Settings());
        client.setName(scanner, out);
        System.setIn(System.in);

        assertThat(client.getName()).isEqualTo(name);
        assertThat(output.toString()).isEqualTo("New member: " + name + "\r\n");
    }

    @Test
    void runWithoutServer_failed() throws IOException {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        Client client = new Client(Settings.GetSettings());
        client.run();
        String output = outputStreamCaptor.toString();
        assertThat(output).contains("Connection refused");

        System.setOut(System.out);
    }
}