package quotestore.http.response;

public class HtmlResponse extends Response {

    private String html;

    public HtmlResponse(String html) {
        this.html = html;
    }

    @Override
    public String getResponseString() {
        StringBuilder response = new StringBuilder();
        response.append("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n");
        response.append(html);
        return response.toString();
    }
}
