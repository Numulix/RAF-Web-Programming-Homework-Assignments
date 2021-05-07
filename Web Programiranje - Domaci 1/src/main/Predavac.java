package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Predavac implements Runnable {

    public EventStream eventStream;
    private List<Event> allEvents = new ArrayList<>();

    @Override
    public void run() {
        while (true) {
            try {
                Event event = eventStream.takeEvent();
                if (event.getEventType().equals("STARTED")) {
                    allEvents.add(event);
                    event.setResult(Thread.currentThread().getName());
                } else if (event.getEventType().equals("FINISHED")) {
                    event.setResult(ThreadLocalRandom.current().nextInt(1, 11));

                    // zavrsen dogadjaj
                    List<Event> oldEvents = new ArrayList<>();

                    for (Event e: allEvents) {
                        if (e.getEventSender().equals(event.getEventSender())) {
                            oldEvents.add(e);
                        }
                    }

                    allEvents.removeAll(oldEvents);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
