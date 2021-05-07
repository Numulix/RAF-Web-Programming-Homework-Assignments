package main;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AsistentStream extends EventStream {

    private ReentrantLock lock;

    public AsistentStream() {
        this.events = new SynchronousQueue<>();
        this.lock = new ReentrantLock();
    }

    @Override
    public boolean lock() throws InterruptedException {
        return this.lock.tryLock(700, TimeUnit.MILLISECONDS);
    }

    @Override
    public void unlock() {
        this.lock.unlock();
    }
}
