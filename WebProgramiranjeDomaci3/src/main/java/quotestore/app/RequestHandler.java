package quotestore.app;

import quotestore.http.HttpMethod;
import quotestore.http.Request;
import quotestore.http.response.Response;

public class RequestHandler {
    public Response handle(Request request) throws Exception {

        if (request.getPath().equals("/quotes") && request.getMethod().equals(HttpMethod.GET)) {
            return (new QuotesController(request)).doGet();
        } else if (request.getPath().equals("/save-quote") && request.getMethod().equals(HttpMethod.POST)) {
            return (new QuotesController(request)).doPost();
        }

        throw new Exception("Page: " + request.getPath() + ". Method: " + request.getMethod() + " not found!");

    }
}
