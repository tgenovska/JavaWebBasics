package javache.http;

import javache.WebConstants;

import java.util.*;

public class HttpResponseImpl implements HttpResponse {
    private int statusCode;
    private Map<String, String> headers;
    private HashMap<String, String> supportedContentTypes;
    private byte[] content;
    private byte[] bytes;

    private ResourceStatus responseCode;
    private String resourceExtension;


    public HttpResponseImpl(String requestContent, byte[] resourceData, String responseStatus, String resourceExtension){
        this.headers = new LinkedHashMap<>();
        this.supportedContentTypes = new HashMap<>();
        this.content = resourceData;
        this.setResourceStatus(responseStatus);
        this.seedSupportedContentTypes();
        this.resourceExtension = resourceExtension;
    }

    private void fillHeaders() {
        this.addHeader(WebConstants.SERVER_HEADER, WebConstants.SERVER_HEADER_NAME_AND_VERSION);
        this.addHeader(WebConstants.DATE_HEADER, this.getNewDate().toString());
        if(this.content != null){
            String contentType = this.supportedContentTypes.get(resourceExtension);
            this.addHeader(WebConstants.CONTENT_TYPE_HEADER, contentType);
            this.addHeader(WebConstants.CONTENT_DISPOSITION_HEADER, WebConstants.CONTENT_DISPOSITION_VALUE_INLINE);
            this.addHeader(WebConstants.CONTENT_LENGTH_HEADER, String.valueOf(this.content.length));

        }
    }

    private Date getNewDate() {
        return new Date();
    }

    private void seedSupportedContentTypes() {
        this.supportedContentTypes.put("png", "image/png");
        this.supportedContentTypes.put("jpg", "image/jpeg");
        this.supportedContentTypes.put("jpeg", "image/jpeg");
        this.supportedContentTypes.put("html", "text/html");

    }

    private void setResourceStatus(String responseStatus) {
        this.responseCode = ResourceStatus.valueOf(responseStatus);
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public int getStatusCode() {
        return this.responseCode.getValue();
    }

    @Override
    public byte[] getContent() {
        return this.content;
    }

    @Override
    public byte[] getBytes() {
        this.fillHeaders();
        StringBuilder responseHeaders = new StringBuilder();
        responseHeaders.append(this.responseCode.getText()).append(System.lineSeparator());

        for (String key : headers.keySet()) {
            responseHeaders.append(key).append(headers.get(key)).append(System.lineSeparator());
        }

        byte[] headersAsBytes = responseHeaders.toString().getBytes();

        byte[] fullResponseByteData = new byte[headersAsBytes.length + this.content.length];

        for (int i = 0; i < headersAsBytes.length; i++) {
            fullResponseByteData[i] = headersAsBytes[i];
        }

        for (int i = 0; i < this.content.length; i++) {
            fullResponseByteData[i + headersAsBytes.length] = this.content[i];
        }

        return fullResponseByteData;

    }

    @Override
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }


    @Override
    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.put(header, value);
    }
}
