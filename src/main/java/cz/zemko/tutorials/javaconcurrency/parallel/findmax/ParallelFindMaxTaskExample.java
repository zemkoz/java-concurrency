package cz.zemko.tutorials.javaconcurrency.parallel.findmax;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public final class ParallelFindMaxTaskExample {
    private static final int NUMBERS_COUNT = 100_000;
    private static final Random randomGenerator = new Random();

    public static void main(String[] args) {
        List<Integer> aList = new ArrayList<>(NUMBERS_COUNT);
        for (int i = 0; i < NUMBERS_COUNT; i++) {
            aList.add(randomGenerator.nextInt(1000_000));
        }
        System.out.println("List: " + aList);

        long startTimeMillis = System.currentTimeMillis();

        var pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        var maxNumber = pool.invoke(ParallelFindMaxTask.of(aList));
        long elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis;

        System.out.println("Maximum value in the list is " + maxNumber + ".");
        System.out.println("The calculation took " + elapsedTimeMillis + "ms.");
    }
}
