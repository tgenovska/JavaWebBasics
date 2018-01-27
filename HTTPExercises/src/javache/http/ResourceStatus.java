package javache.http;

public enum ResourceStatus {
    OK(200, "HTTP/1.1 200 OK"), NotFound(404, "HTTP/1.1 404 Not Found"), Unauthorized(401, "HTTP/1.1 401 Unauthorized"), ServerError(500, "HTTP/1.1 500 Internal Server Error");

    private int value;
    private String text;

    ResourceStatus(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

}
