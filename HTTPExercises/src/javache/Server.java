package javache;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.FutureTask;


public class Server  {
    private int port;
    private ServerSocket server;
    private int timeout;

    public Server(int port) {
        this.port = port;
        this.timeout = 0;
    }

    public void run() throws IOException {
        this.server = new ServerSocket(this.port);
        this.server.setSoTimeout(WebConstants.SOCKET_TIMEOUT_MILLISECONDS);

        System.out.println("Listening on port: " + this.port);

        while(true) {
            try(Socket clientSocket = this.server.accept()) {
                clientSocket.setSoTimeout(5000);
                System.out.println("Client connected: " + clientSocket.getPort());
                RequestHandler requestHandler = new RequestHandler(clientSocket);
                ConnectionHandler connectionHandler = new ConnectionHandler(clientSocket, requestHandler);
                FutureTask<?> futureTask = new FutureTask<>(connectionHandler,null);
                futureTask.run();
            } catch(SocketTimeoutException e) {
                System.out.println("Timeout detected!");
                this.timeout++;
            }
        }
    }
}
