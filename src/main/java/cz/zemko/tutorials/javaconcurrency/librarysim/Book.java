package cz.zemko.tutorials.javaconcurrency.librarysim;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class Book {
    private static final Random RANDOM = new Random();

    private final String title;
    private final Lock lock = new ReentrantLock(true);

    public Book(String title) {
        this.title = title;
    }

    void read(Student student) {
        if (lock.tryLock()) {
            System.out.println("Student '" + student.getName() + "' is reading book '" + title + "'.");
            try {
                Thread.sleep(RANDOM.nextInt(5000));
            } catch (InterruptedException e) {
                System.out.println("Thread '" + Thread.currentThread().getName() + "' was interrupted: " + e);
            } finally {
                lock.unlock();
            }
            System.out.println("Student '" + student.getName() + "' finished read book '" + title + "'.");
        } else {
            System.out.println(
                    "Book '" + title + "' is read by someone else. Student '" + student.getName() + "' can't read it yet.");
        }
    }
}
