package main;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public abstract class EventStream {

    public BlockingQueue<Event> events;
    public abstract boolean lock() throws InterruptedException;
    public abstract void unlock();

    public boolean openConnection() throws InterruptedException {
        return lock();
    }

    public void endConnection() {
        unlock();
    }

    public boolean offerEvent(Event event) throws InterruptedException {
        return this.events.offer(event, 2, TimeUnit.SECONDS);
    }

    public Event takeEvent() throws InterruptedException {
        try {
            return this.events.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("GOTOVA ODBRANA");
        }
        return this.events.take();
    }

    public Object getResult(Event event) throws InterruptedException {
        return event.getResult();
    }
}
