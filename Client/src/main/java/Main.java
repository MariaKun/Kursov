import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Client client = new Client(Settings.GetSettings());
        client.run();
    }
}
