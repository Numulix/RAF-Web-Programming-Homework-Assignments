package main;

import java.util.concurrent.*;

public class ProfesorStream extends EventStream {

    private Semaphore semafor;
    private CyclicBarrier barijera;

    public ProfesorStream() {
        this.events = new LinkedBlockingQueue<>();
        this.semafor = new Semaphore(2);
        this.barijera = new CyclicBarrier(2);
    }

    @Override
    public boolean lock() throws InterruptedException {
        try {
            boolean isAcquired = semafor.tryAcquire(700, TimeUnit.MILLISECONDS);

            if (!isAcquired) return false;

            this.barijera.await(700, TimeUnit.MILLISECONDS);
        } catch (BrokenBarrierException e) {
            return false;
        } catch (TimeoutException e) {
            this.semafor.release();
            return false;
        }

        return true;
    }

    @Override
    public void unlock() {
        this.semafor.release();
    }
}
