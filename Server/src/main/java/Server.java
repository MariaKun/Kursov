import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private static Logger logger = LogManager.getLogger("server");
    public CopyOnWriteArrayList<ThreadForServer> threads = new CopyOnWriteArrayList<>();
    private Settings settings;

    public Server(Settings settings) {
        this.settings = settings;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(settings.getPort())) {
            logger.info("ServerSocket is created");
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept(); // ждем подключения
                logger.info("New connection accepted");
                ThreadForServer threadForServer = new ThreadForServer(this, clientSocket);
                threads.add(threadForServer);
                threadForServer.start();
            }
            for (ThreadForServer threadForServer : threads) {
                threadForServer.interrupt();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void sendToAll(String word) {
        for (ThreadForServer threadForServer : threads) {
            threadForServer.send(word);
        }
    }
}
