package javache;

public final class WebConstants {
    public static final int SOCKET_TIMEOUT_MILLISECONDS = 15000;

    public static final String SERVER_HEADER = "Server: ";

    public static final String SERVER_HEADER_NAME_AND_VERSION = "Javache/-1.0.0";

    public static final String DATE_HEADER = "Date: ";

    public static final String CONTENT_TYPE_HEADER = "Content-Type: ";

    public static final String CONTENT_DISPOSITION_HEADER = "Content-Disposition: ";

    public static final String CONTENT_LENGTH_HEADER = "Content-Length: ";

    public static final String CONTENT_DISPOSITION_VALUE_INLINE = "inline";

    public static final String CONTENT_DISPOSITION_VALUE_ATTACHMENT = "attachment";

    private WebConstants() { }
}
