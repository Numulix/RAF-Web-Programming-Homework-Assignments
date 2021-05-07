package client;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ClientMain {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Unesite broj igraca: ");
        int n = in.nextInt();
        in.close();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(n);

        for (int i = 0; i < n; i++) {
            scheduledExecutorService.schedule(new ClientThread(),
                    ThreadLocalRandom.current().nextInt(1, 1001), TimeUnit.MILLISECONDS);
        }

        scheduledExecutorService.shutdown();
    }

}
