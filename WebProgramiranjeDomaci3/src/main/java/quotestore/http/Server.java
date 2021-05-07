package quotestore.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int TCP_PORT = 8080;

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(TCP_PORT);
            System.out.println("Server listening on port " + TCP_PORT);
            while (true) {
                Socket s = ss.accept();
                new Thread(new ServerThread(s)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
