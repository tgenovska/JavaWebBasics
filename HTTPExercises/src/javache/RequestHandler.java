package javache;

import javache.http.HttpRequest;
import javache.http.HttpRequestImpl;
import javache.http.HttpResponse;
import javache.http.HttpResponseImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler {
    private Socket clientSocket;

    private Map<String, String> supportedContentTypes;

    private Map<String, String> responseLines;

    private String resourceStatus;

    private String resourceExtension;

    private Integer resourceSize;

    public RequestHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
        this.supportedContentTypes = new HashMap<>();
        this.responseLines = new HashMap<>();
    }

    public byte[] handleRequest(String requestContent){
        HttpRequest request = new HttpRequestImpl(requestContent);
        byte[] resourceData = null;

        if(request.getMethod().equals("GET")){
            if(request.isResource()){
                resourceData = this.getResource(request.getRequestUrl());
            }
        }

        HttpResponse response = new HttpResponseImpl(requestContent, resourceData, this.resourceStatus, this.resourceExtension);
        return response.getBytes();

    }

    private byte[] getResource(String requestUrl) {
        byte[] fileByteData = null;

        try {
            fileByteData = Files.readAllBytes(Paths.get("D:\\SoftUni\\JavaWebBasics\\HTTPExercises\\src\\resources" + requestUrl));
            this.resourceExtension = requestUrl.substring(requestUrl.lastIndexOf(".") + 1);
            this.resourceSize = fileByteData.length;
            this.resourceStatus = "OK";
        } catch (NoSuchFileException e) {
            this.resourceStatus = "NotFound";
        } catch (AccessDeniedException e) {
            this.resourceStatus = "Unauthorized";
        } catch (IOException e) {
            this.resourceStatus = "ServerError";
            e.printStackTrace();
        }

        return fileByteData;
    }


}
