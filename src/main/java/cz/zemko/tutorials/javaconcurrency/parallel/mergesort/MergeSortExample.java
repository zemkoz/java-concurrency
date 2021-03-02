package cz.zemko.tutorials.javaconcurrency.parallel.mergesort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public final class MergeSortExample {
    private static final int NUMBERS_COUNT = 10_000_000;
    private static final Random randomGenerator = new Random();

    public static void main(String[] args) {
        List<Integer> aList = new ArrayList<>(NUMBERS_COUNT);
        for (int i = 0; i < NUMBERS_COUNT; i++) {
            aList.add(randomGenerator.nextInt(1000_000));
        }
        System.out.println("Input list: " + aList);
        System.out.println("-----------------------------------------------------");
        System.out.println();

        long startTimeMillis = System.currentTimeMillis();

        var pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        var sortedList = pool.invoke(MergeSortTask.of(aList));
        long elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis;

        System.out.println("Sorted list: " + sortedList + ".");
        System.out.println("The calculation took " + elapsedTimeMillis + "ms.");
    }
}
