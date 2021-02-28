package cz.zemko.tutorials.javaconcurrency.librarysim;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class LibrarySimulator {
    private static final long SIMULATION_TIME_IN_MILLIS = 60_000; // 1 minute
    private static final int STUDENTS_COUNT = 4;
    private static final int BOOKS_COUNT = 6;

    public static void main(String[] args) throws InterruptedException {

        var bookList = new ArrayList<Book>(BOOKS_COUNT);
        for (int i = 0; i < BOOKS_COUNT; i++) {
            bookList.add(new Book("b" + i));
        }

        var studentList = new ArrayList<Student>(STUDENTS_COUNT);
        for (int i = 0; i < STUDENTS_COUNT; i++) {
            studentList.add(new Student("s" + i, bookList));
        }

        System.out.println("Library simulation is started.");
        ExecutorService executorService = Executors.newFixedThreadPool(studentList.size());
        studentList.forEach(executorService::execute);
        try {
            Thread.sleep(SIMULATION_TIME_IN_MILLIS);
        } finally {
            studentList.forEach(Student::shutdown);
            executorService.shutdown();
        }
        System.out.println("Library simulation ended.");
    }
}
