package quoteoftheday.http;

import com.google.gson.Gson;
import quoteoftheday.app.QuoteList;
import quotestore.http.HttpMethod;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.StringTokenizer;

public class ServerThread implements Runnable {

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private Gson gson;
    private Random random;

    public ServerThread(Socket client) {
        this.client = client;
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
            gson = new Gson();
            random = new Random();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String reqLine = in.readLine();

            StringTokenizer st = new StringTokenizer(reqLine);

            String method = st.nextToken(); // METODA
            // String path = st.nextToken(); // PATH

            System.out.println("\nHTTP CLIENT REQUEST:\n");
            do {
                System.out.println(reqLine);
                reqLine = in.readLine();
            } while (!reqLine.trim().equals(""));

            if (method.equals(HttpMethod.GET.toString())) {
                StringBuilder quoteResponse = new StringBuilder();
                int randomQuoteIndex = random.nextInt(10);
                quoteResponse.append("HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n");
                quoteResponse.append(gson.toJson(QuoteList.quotesOfTheDay[randomQuoteIndex]));

                System.out.println("\nQUOTE OF THE DAY SERVER RESPONSE:\n");
                System.out.println(quoteResponse.toString());

                out.println(quoteResponse.toString());
            } else {
                StringBuilder quoteResponse = new StringBuilder();
                quoteResponse.append("HTTP/1.1 404 Not Found\r\n\r\n");

                out.println(quoteResponse.toString());
            }

            in.close();
            out.close();
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
