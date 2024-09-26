import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Logger logger = LogManager.getLogger("client");
    private String name;
    private Settings settings;

    public Client(Settings settings) {
        this.settings = settings;
    }

    public void run() {
        try (Socket clientSocket = new Socket(settings.getHost(), settings.getPort())) {
            logger.info("ClientSocket is created");
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            logger.info("To exit type 'exit'");
            Scanner scanner = new Scanner(System.in);
            setName(scanner, out);

            Thread readMessages = new Thread(() ->
            {
                while (clientSocket.isConnected() && !Thread.interrupted()) {
                    String s = "";
                    try {
                        s = in.readLine();
                        if (s != null) {
                            logger.info(s);
                        }
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                }
            });
            readMessages.start();

            String text = "";
            while (!text.equals("exit")) {
                text = scanner.nextLine();
                out.println(name + " : " + text);
            }

            readMessages.interrupt();
            out.println("Left the chat: " + name);
            scanner.close();
            out.close();
            in.close();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(Scanner scanner, PrintWriter out) {
        logger.info("Enter your name:");
        String name = scanner.nextLine();
        out.println("New member: " + name);
        this.name = name;
    }
}
