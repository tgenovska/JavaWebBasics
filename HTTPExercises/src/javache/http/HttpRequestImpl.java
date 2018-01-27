package javache.http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestImpl implements HttpRequest {
    private String method;
    private String requestUrl;
    Map<String, String> headers;
    Map<String, String> bodyParameters;



    public HttpRequestImpl(String requestContent) {
        this.headers = new HashMap<>();
        this.bodyParameters = new HashMap<>();

        this.parseRequest(requestContent);
    }



    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public Map<String, String> getBodyParameters() {
        return this.bodyParameters;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;

    }

    @Override
    public String getRequestUrl() {
        return this.requestUrl;
    }

    @Override
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;

    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.put(header, value);
    }

    @Override
    public void addBodyParameter(String parameter, String value) {
        this.bodyParameters.put(parameter, value);
    }

    @Override
    public boolean isResource() {
        return this.requestUrl.contains(".");
    }

    private void parseRequest(String requestContent) {
        String[] requestLines = requestContent.split("\r\n");
        String[] requestFirstLine = requestLines[0].trim().split("\\s+");

        if(requestFirstLine.length !=3 || !requestFirstLine[2].toLowerCase().equals("http/1.1")){
            throw new IllegalArgumentException("INVALID REQUEST!");
        }

        if(requestFirstLine[0].equals("GET")){
            this.method = "GET";
        } else {
            this.method = "POST";

        }

        this.requestUrl = requestFirstLine[1];


       // this.parseHeaders(requestLines, endIndex);
       // this.parseBodyParameters(requestLines[endIndex + 1]);
        

    }

    private void parseBodyParameters(String requestLine) {
        String[] pairs = requestLine.split("&");
        for (String pair : pairs) {
            String[] params = pair.split("=");
            this.addBodyParameter(params[0], params[1]);
        }
    }

    private void parseHeaders(String[] requestLines, int endIndex) {

        for (int i = 1; i < endIndex; i++) {
            String[] headersData = requestLines[i].split(":\\s+");
            this.addHeader(headersData[0], headersData[1]);
        }
    }

}

