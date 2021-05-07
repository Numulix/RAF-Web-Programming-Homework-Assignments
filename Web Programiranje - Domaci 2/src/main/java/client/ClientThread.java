package client;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class ClientThread implements Runnable {

    private UUID id;
    private int poeni;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private boolean smesten;

    private Gson gson;

    public ClientThread() {
        id = UUID.randomUUID();
        poeni = 0;
        gson = new Gson();
        smesten = true;

        try {
            socket = new Socket("localhost", 1337);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {
            System.out.println("Igrac sa id-jem: " + id + " je kreiran.");

            while (smesten) {
                String poruka = in.readLine();
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
