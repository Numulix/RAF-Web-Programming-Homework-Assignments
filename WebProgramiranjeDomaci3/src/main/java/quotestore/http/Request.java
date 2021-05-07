package quotestore.http;

import java.util.HashMap;

public class Request {

    private HttpMethod method;
    private String path;
    private HashMap<String, String> params = null;

    public Request(HttpMethod method, String path, HashMap<String, String> params) {
        this.method = method;
        this.path = path;
        this.params = params;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public HashMap<String, String> getParams() {
        return params;
    }
}
