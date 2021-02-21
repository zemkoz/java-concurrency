package cz.zemko.tutorials.javaconcurrency.diningphilosophers;

import java.util.Random;

public final class Philosopher implements Runnable {
    private static final long PICKUP_FORK_TIMEOUT = 10;

    private final int id;
    private final Fork leftFork;
    private final Fork rightFork;
    private final Random randomGenerator;

    private boolean isLive;
    private int eatingCounter;

    public Philosopher(int id, Fork leftFork, Fork rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.randomGenerator = new Random();
        this.isLive = true;
        this.eatingCounter = 0;
    }

    @Override
    public void run() {
        while (isLive) {
            try {
                if (leftFork.tryPickUp(PICKUP_FORK_TIMEOUT)) {
                    if (rightFork.tryPickUp(PICKUP_FORK_TIMEOUT)) {
                        eat();
                        rightFork.putDown();
                    }

                    leftFork.putDown();
                }
                think();
            } catch (InterruptedException e) {
                System.err.println("Philosopher-" + id + " was interrupted.");
            }
        }
    }

    public void shutdown() {
        isLive = false;
    }

    @Override
    public String toString() {
        return "Philosopher{" +
                "id=" + id +
                ", eatingCounter=" + eatingCounter +
                '}';
    }

    private void eat() throws InterruptedException {
        var millis = randomGenerator.nextInt(3000);
        System.out.println("Philosopher-" + id + " is eating " + millis + "ms.");
        eatingCounter++;
        Thread.sleep(millis);
        System.out.println("Philosopher-" + id + " finished eating.");
    }

    private void think() throws InterruptedException {
        var millis = randomGenerator.nextInt(5000);
        System.out.println("Philosopher-" + id + " is thinking " + millis + "ms.");
        Thread.sleep(millis);
        System.out.println("Philosopher-" + id + " finished thinking.");
    }
}
