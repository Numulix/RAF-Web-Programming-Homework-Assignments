package quotestore.app;

import quotestore.http.Request;
import quotestore.http.response.HtmlResponse;
import quotestore.http.response.RedirectResponse;
import quotestore.http.response.Response;

public class QuotesController extends Controller{

    public QuotesController(Request request) {
        super(request);
    }

    @Override
    public Response doGet() {
        Quote q = QoDGetter.getInstance().getQuoteOfTheDay();
        StringBuilder html = new StringBuilder();
        html.append("<html>\n<head>\n<title>\nOdgovor servera\n</title>\n</head>\n");
        html.append("<body>\n");
        html.append("<form action=\"/save-quote\" method=\"post\">\n" +
                "  <label for=\"author\">Author:</label>\n" +
                "  <input name=\"author\" id=\"author\" type=\"text\"><br><br>\n" +
                "  <label for=\"quote\">Quote: </label>\n" +
                "  <input name=\"quote\" id=\"quote\" type=\"text\"><br><br>\n" +
                "  <button type=\"submit\">Save Quote</button>\n" +
                "</form>\n" +
                "<h2>Quote of the day:</h2>\n" +
                "<p><b>" + q.getAuthor() + "</b> - \"" + q.getText() + "\"</p>\n" +
                "<h2>Saved quotes:</h2>");

        for (Quote quote: QuotesStorage.getInstance().getAllQuotes()) {
            html.append("<p>----------------------------------------</p>\n" +
                    "<p><b>" + quote.getAuthor() + "</b> - \"" + quote.getText() + "\"</p>\n" +
                    "<p>----------------------------------------</p>\n");
        }

        html.append("</body>\n</html>");

        return new HtmlResponse(html.toString());
    }

    @Override
    public Response doPost() {
        QuotesStorage.getInstance().addQuote(getRequest().getParams().get("author"),
                getRequest().getParams().get("quote"));

        return new RedirectResponse();
    }
}
