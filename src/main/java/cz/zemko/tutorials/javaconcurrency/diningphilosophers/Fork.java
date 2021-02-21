package cz.zemko.tutorials.javaconcurrency.diningphilosophers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class Fork {
    private final Lock lock = new ReentrantLock(true);

    public boolean tryPickUp(long timeout) throws InterruptedException {
        return lock.tryLock(timeout, TimeUnit.MILLISECONDS);
    }

    public void putDown() {
        lock.unlock();
    }
}
