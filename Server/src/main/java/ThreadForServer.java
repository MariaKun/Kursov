import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class ThreadForServer extends Thread {
    private Logger logger = LogManager.getLogger("server");
    private BufferedReader in; // поток чтения из сокета
    private PrintWriter out; // поток записи в сокет
    private Server server;

    public ThreadForServer(Server server, Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        this.server = server;
    }

    @Override
    public void run() {
        String word = "";
        try {
            while (word != null && !word.equals("exit")) {
                word = in.readLine();
                if (word!=null)
                {
                    logger.info(word);
                    server.sendToAll(word);
                }
            }
            in.close();
            out.close();
        } catch (Exception e) {
            logger.info(e.getMessage());
        } finally {
            server.threads.remove(this);
            interrupt();
        }
    }

    public void send(String msg) {
        out.println(msg);
    }
}



