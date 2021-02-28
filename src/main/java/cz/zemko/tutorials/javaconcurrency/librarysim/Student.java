package cz.zemko.tutorials.javaconcurrency.librarysim;

import java.util.List;
import java.util.Random;

public final class Student implements Runnable {
    private static final Random RANDOM = new Random();

    private final String name;
    private final List<Book> bookList;
    private boolean isLive;

    public Student(String name, List<Book> bookList) {
        this.name = name;
        this.bookList = bookList;
        isLive = true;
    }

    public String getName() {
        return name;
    }

    public void shutdown() {
        isLive = false;
    }

    @Override
    public void run() {
        while (isLive) {
            int index = RANDOM.nextInt(bookList.size());
            bookList.get(index).read(this);
        }
    }
}
