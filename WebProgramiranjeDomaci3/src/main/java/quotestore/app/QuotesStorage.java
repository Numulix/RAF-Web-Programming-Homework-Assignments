package quotestore.app;

import java.util.ArrayList;

public class QuotesStorage {

    public static QuotesStorage instance = null;

    private ArrayList<Quote> allQuotes;

    private QuotesStorage() {
        allQuotes = new ArrayList<>();
    }

    public static QuotesStorage getInstance() {
        if (instance == null) {
            instance = new QuotesStorage();
        }
        return instance;
    }

    public void addQuote(String author, String text) {
        allQuotes.add(new Quote(author, text));
    }

    public ArrayList<Quote> getAllQuotes() {
        return allQuotes;
    }
}
