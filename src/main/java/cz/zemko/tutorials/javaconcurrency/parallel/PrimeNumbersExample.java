package cz.zemko.tutorials.javaconcurrency.parallel;

import java.util.stream.IntStream;

public class PrimeNumbersExample {
    private static final int HIGHER_BOUND = 10_000_000;

    public static void main(String[] args) {

        System.out.println("Sequential counting of primes:");
        long startTime = System.currentTimeMillis();
        long primesCount = IntStream.range(1, HIGHER_BOUND)
                .filter(PrimeNumbersExample::isPrime)
                .count();
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Count of Primes between 1 and " + HIGHER_BOUND + " is " + primesCount + ". The operation took " + elapsedTime + "ms.");
        System.out.println();

        System.out.println("Parallel counting of primes:");
        startTime = System.currentTimeMillis();
        primesCount = IntStream.range(1, HIGHER_BOUND)
                .parallel()
                .filter(PrimeNumbersExample::isPrime)
                .count();
        elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Count of Primes between 1 and " + HIGHER_BOUND + " is " + primesCount + ". The operation took " + elapsedTime + "ms.");
    }

    private static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        } else if (num == 2) {
            return true;
        } else if (num % 2 == 0) {
            return false;
        }

        long maxDivider = (long) Math.sqrt(num);
        for (int i = 3; i <= maxDivider; i+=2) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
