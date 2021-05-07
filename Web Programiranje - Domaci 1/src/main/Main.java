package main;

import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger sumaOcena = new AtomicInteger();
    public static AtomicInteger brojStudenata = new AtomicInteger();
    public static long pocetnoVreme;

    public static void main(String[] args) {

        System.out.print("Unesite broj studenata: ");
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        in.close();

        EventStream profesorStream = new ProfesorStream();
        EventStream asistentStream = new AsistentStream();

        Predavac profesor = new Profesor(profesorStream);
        Predavac asistent = new Asistent(asistentStream);

        ExecutorService predavacExecutor = Executors.newFixedThreadPool(2);
        predavacExecutor.execute(profesor);
        predavacExecutor.execute(asistent);

        ScheduledExecutorService studentExecutor = Executors.newScheduledThreadPool(30);

        pocetnoVreme = System.currentTimeMillis();

        for (int i = 0; i < N; i++) {
            Student student = new Student(profesorStream, asistentStream);
            studentExecutor.schedule(student, ThreadLocalRandom.current().nextInt(1, 1001)
                    , TimeUnit.MILLISECONDS);
        }

        try {
            Thread.sleep(5000);
            studentExecutor.shutdownNow();
            predavacExecutor.shutdownNow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Zbir ocena: " + sumaOcena.intValue());
        System.out.println("Broj studenata koji je branilo: " + brojStudenata.intValue());
        System.out.format("PROSEK: %.2f\n", sumaOcena.doubleValue() / brojStudenata.doubleValue());
    }

}
