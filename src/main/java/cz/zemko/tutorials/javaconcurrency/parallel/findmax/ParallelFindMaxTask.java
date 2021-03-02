package cz.zemko.tutorials.javaconcurrency.parallel.findmax;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public final class ParallelFindMaxTask<T extends Comparable<T>> extends RecursiveTask<T> {
    private final List<T> aList;
    private final int leftIndex;
    private final int rightIndex;

    private ParallelFindMaxTask(final List<T> aList, final int leftIndex, final int rightIndex) {
        this.aList = aList;
        this.leftIndex = leftIndex;
        this.rightIndex = rightIndex;
    }

    public static <T extends Comparable<T>> ParallelFindMaxTask<T> of(final List<T> aList) {
        return new ParallelFindMaxTask<>(aList, 0, aList.size() - 1);
    }

    @Override
    protected T compute() {
        if (leftIndex >= rightIndex) {
            return aList.get(leftIndex);
        }

        var middleIndex = (leftIndex + rightIndex) / 2;
        var leftTask = new ParallelFindMaxTask<>(aList, leftIndex, middleIndex);
        var rightTask = new ParallelFindMaxTask<>(aList, middleIndex + 1, rightIndex);

        invokeAll(leftTask, rightTask);
        var leftTaskMax = leftTask.join();
        var rightTaskMax = rightTask.join();
        return leftTaskMax.compareTo(rightTaskMax) > 0 ? leftTaskMax : rightTaskMax;
    }
}
