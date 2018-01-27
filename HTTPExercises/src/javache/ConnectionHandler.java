package javache;

import javache.io.Reader;
import javache.io.Writer;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler  extends Thread {
    private Socket clientSocket;
    private RequestHandler requestHandler;
    private InputStream clientSocketInputStream;
    private OutputStream clientSocketOutputStream;

    public ConnectionHandler(Socket clientSocket, RequestHandler requestHandler){
        this.requestHandler = requestHandler;
        this.initializeHandler(clientSocket);
    }

    private void initializeHandler(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            this.clientSocketInputStream = new DataInputStream(clientSocket.getInputStream());
            this.clientSocketOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){

        try {
            String requestContent = Reader.readAllLines(this.clientSocketInputStream);
            byte[] responseContent = this.requestHandler.handleRequest(requestContent);

            Writer.writeBytes(responseContent, this.clientSocketOutputStream);
            this.clientSocketInputStream.close();
            this.clientSocketOutputStream.close();
            this.clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
