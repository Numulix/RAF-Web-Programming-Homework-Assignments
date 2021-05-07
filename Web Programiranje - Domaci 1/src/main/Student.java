package main;

import java.util.concurrent.ThreadLocalRandom;

public class Student implements Runnable {

    private EventStream profesorStream;
    private EventStream asistentStream;
    private int trajanje;
    private long pocetnoVreme;
    private String nitPredavaca;

    public Student(EventStream profesorStream, EventStream asistentStream) {
        this.profesorStream = profesorStream;
        this.asistentStream = asistentStream;
    }

    private int getOcena(EventStream stream) throws InterruptedException {
        boolean isSuccess = stream.openConnection();
        int ocena;

        if (!isSuccess) return -1;

        this.pocetnoVreme = System.currentTimeMillis();
        Event startEvent = new Event(Thread.currentThread().getName(), "STARTED");

        if (!stream.offerEvent(startEvent)) {
            throw new InterruptedException();
        }

        try {
            this.nitPredavaca = (String) stream.getResult(startEvent);

            this.trajanje = ThreadLocalRandom.current().nextInt(500,1001);
            Thread.sleep(this.trajanje);

            Event finishedEvent = new Event(Thread.currentThread().getName(), "FINISHED");
            stream.offerEvent(finishedEvent);
            ocena = (int) stream.getResult(finishedEvent);
        } catch (InterruptedException e) {
            ocena = (int) stream.getResult(startEvent);
            Thread.currentThread().interrupt();
        } finally {
            stream.endConnection();
        }

        return ocena;
    }

    @Override
    public void run() {
        long vremePristizanja = System.currentTimeMillis();
        int ocena = -1;
        boolean profesorSlobodan = ThreadLocalRandom.current().nextBoolean();

        while (ocena == -1) {
            EventStream stream = this.asistentStream;
            StringBuilder sb = new StringBuilder();

            try {

                if (profesorSlobodan) {
                    stream = this.profesorStream;
                }

                ocena = getOcena(stream);

                if (ocena != -1) {
                    sb.append("Thread: ");
                    sb.append(Thread.currentThread().getName());

                    sb.append(" Arrival: ");
                    sb.append(vremePristizanja - Main.pocetnoVreme);

                    sb.append(" Prof: ");
                    sb.append(nitPredavaca);

                    sb.append(" TTC: ");
                    sb.append(trajanje);
                    sb.append(":");
                    sb.append(pocetnoVreme - Main.pocetnoVreme);

                    sb.append(" Score: ");
                    sb.append(ocena);

                    System.out.println(sb.toString());

                    Main.sumaOcena.addAndGet(ocena);
                    Main.brojStudenata.incrementAndGet();
                    break;
                }
                profesorSlobodan = !profesorSlobodan;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
