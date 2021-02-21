package cz.zemko.tutorials.javaconcurrency.diningphilosophers;

import java.util.ArrayList;
import java.util.stream.Collectors;

public final class DiningPhilosophers {
    private static final long SIMULATION_TIME_IN_MILLIS = 60_000; // 1 minute
    private static final int FORKS_COUNT = 5;
    private static final int PHILOSOPHERS_COUNT = 5;

    public static void main(String[] args) throws InterruptedException {
        var forkList = new ArrayList<Fork>(FORKS_COUNT);
        for (int i = 0; i < FORKS_COUNT; i++) {
            forkList.add(new Fork());
        }

        var philosophersList = new ArrayList<Philosopher>(PHILOSOPHERS_COUNT);
        for (int i = 0; i < PHILOSOPHERS_COUNT; i++) {
            var leftFork = forkList.get(i);
            var rightFork = forkList.get((i + 1) % FORKS_COUNT);

            var philosopher = new Philosopher(i + 1, leftFork, rightFork);
            philosophersList.add(philosopher);
        }

        System.out.println("Simulation start...");
        var  threadsList = philosophersList.stream()
                .map(Thread::new)
                .collect(Collectors.toList());
        threadsList.forEach(Thread::start);

        Thread.sleep(SIMULATION_TIME_IN_MILLIS);

        philosophersList.forEach(Philosopher::shutdown);
        for (Thread thread : threadsList) {
            thread.join();
        }

        System.out.println("Simulation finished...");
        System.out.println();
        System.out.println("After simulation:");
        System.out.println("------------------------");
        philosophersList.forEach(System.out::println);
    }
}
