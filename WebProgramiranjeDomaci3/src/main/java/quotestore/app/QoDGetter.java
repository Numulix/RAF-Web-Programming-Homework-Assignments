package quotestore.app;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

public class QoDGetter {

    public static volatile QoDGetter instance = null;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Gson gson;

    private QoDGetter() {
        gson = new Gson();
    }

    public static QoDGetter getInstance() {
        if (instance == null) {
            synchronized (QoDGetter.class) {
                if (instance == null) {
                    instance = new QoDGetter();
                }
            }
        }
        return instance;
    }

    public Quote getQuoteOfTheDay() {
        try {
            socket = new Socket("localhost", 8081);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            StringBuilder quoteReq = new StringBuilder();
            quoteReq.append("GET / HTTP/1.1\r\nHost: localhost:8081\r\n\r\n");
            out.println(quoteReq.toString());
            String reqLine = in.readLine();

            System.out.println("\nQOD METODA PROBA:\n");

            do {
                System.out.println(reqLine);
                reqLine = in.readLine();
            } while (!reqLine.trim().equals(""));

            String quoteJson = in.readLine();
            System.out.println(quoteJson);

            Quote resQuote = gson.fromJson(quoteJson, Quote.class);

            in.close();
            out.close();
            socket.close();

            return resQuote;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
