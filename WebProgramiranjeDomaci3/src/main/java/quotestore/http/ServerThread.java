package quotestore.http;

import quotestore.app.RequestHandler;
import quotestore.http.response.Response;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.StringTokenizer;

public class ServerThread implements Runnable {

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;

    public ServerThread(Socket client) {
        this.client = client;
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // HTTP: METODA I PUTANJA
            String reqLine = in.readLine();

            StringTokenizer stringTokenizer = new StringTokenizer(reqLine);

            String method = stringTokenizer.nextToken(); // METODA
            String path = stringTokenizer.nextToken(); // PATH

            HashMap<String, String> postParams = new HashMap<>();

            System.out.println("\nHTTP CLIENT REQUEST:\n");
            do {
                System.out.println(reqLine);
                reqLine = in.readLine();
            } while (!reqLine.trim().equals(""));

            if (method.equals(HttpMethod.POST.toString())) {
                char[] buf = new char[2048];
                in.read(buf);
                String params = new String(buf);
                System.out.println(params);
                String[] paramArr = params.split("&");
                for (String keyPair: paramArr) {
                    postParams.put(keyPair.split("=")[0],
                            URLDecoder.decode(keyPair.split("=")[1], StandardCharsets.UTF_8.name()));
                }
            }

            Request request = new Request(HttpMethod.valueOf(method), path, postParams);

            RequestHandler rh = new RequestHandler();
            Response response = rh.handle(request);

            System.out.println("\nHTTP SERVER RESPONSE:\n");
            System.out.println(response.getResponseString());

            out.println(response.getResponseString());

            in.close();
            out.close();
            client.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
