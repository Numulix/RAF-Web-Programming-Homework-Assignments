package quotestore.app;

import quotestore.http.Request;
import quotestore.http.response.Response;

public abstract class Controller {

    private Request request;

    public Controller(Request request) {
        this.request = request;
    }

    public abstract Response doGet();
    public abstract Response doPost();

    public Request getRequest() {
        return request;
    }
}
